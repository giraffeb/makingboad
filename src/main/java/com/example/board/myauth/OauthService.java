package com.example.board.myauth;

import com.example.board.Users.UserTypeRepository;
import com.example.board.Users.Users;
import com.example.board.Users.UsersRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class OauthService {

    private AuthService authService;
    private UsersRepository usersRepository;
    private UserTypeRepository userTypeRepository;

    public OauthService(AuthService authService, UsersRepository usersRepository, UserTypeRepository userTypeRepository) {
        this.authService = authService;
        this.usersRepository = usersRepository;
        this.userTypeRepository = userTypeRepository;
    }

    /***
     *
     * google oauth api를 사용
     * idtoken을 검증해서 확인함.
     * @param idTokenString
     * @return
     * @throws GeneralSecurityException
     * @throws IOException
     */

    public Optional<Users> verifyGoogleOauthToken(String idTokenString) throws GeneralSecurityException, IOException {

        //구글 verifying api를 사용햇씁니다.
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList("1041531983414-1etihq6otuj9kmo6bkkm0c10e9qae8ib.apps.googleusercontent.com"))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        // (Receive idTokenString by HTTPS POST)

        GoogleIdToken idToken = null;
        idToken = verifier.verify(idTokenString);

        Users tempUsers = null;

        if (idToken != null) {

            GoogleIdToken.Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();
            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");

            tempUsers = new Users()
                    .setUserId(userId)
                    .setUsername(name)
                    .setPassword("");

        } else {
            System.out.println("Invalid ID token.");
        }

        return Optional.of(tempUsers);
    }


    public Optional<Users> verifiyFacebookOauthToken(String requestToken) throws IOException {
        boolean flag = false;
        //facebook은 requestToken를 받아서 인증에 사용합시다.
        System.out.println("requestToken: "+requestToken);

        //TODO: 외부 설정파일로 변경해야 될 것 같습니다.
        String clientID = "2208086505911469";
        String clientSecret = "f0be472a841d6b5a7ee9a5b0e78f3693";

//        "https://graph.facebook.com/oauth/access_token?client_id="+clientID+"&client_secret="+clientSecret+"&grant_type=client_credentials"

        //Client App AccessToken을 가져오는 api 입니다.
        //client_id : 클라이언트 아이디
        //client_secret : 클라이언트 시크릿.

        String appAccessTokenQuery="https://graph.facebook.com/oauth/access_token?client_id="+clientID+"&client_secret="+clientSecret+"&grant_type=client_credentials";
        String appAccessTokenResult = this.requestAPI(appAccessTokenQuery, "GET", null);

        JSONObject appAccessTokenJson = new JSONObject(appAccessTokenResult);
        System.out.println(appAccessTokenJson);
        System.out.println(appAccessTokenJson.get("access_token"));

        String appAccessToken = appAccessTokenJson.get("access_token").toString();


        //사용자가 전송한 requestToken을 검증하는 api
        //input_token : 사용자가 전송한 토큰
        //access_token : 클라이언트 엑세스 토큰.
        //"https://graph.facebook.com/debug_token?input_token="+requestToken+"&access_token="+appAccessToken
        String userAccessTokenVerifyQuery = "https://graph.facebook.com/debug_token?input_token="+requestToken+"&access_token="+appAccessToken;
        String validationResult = this.requestAPI(userAccessTokenVerifyQuery, "GET", null);

        JSONObject validJson = new JSONObject(validationResult);
        System.out.println(validJson);
        System.out.println(((JSONObject)validJson.get("data")).get("is_valid"));


        boolean isValid = Boolean.valueOf(((JSONObject)validJson.get("data")).get("is_valid").toString());

        Users tempUser = null;
        //사용자가 보낸 AccessToken 검증결과
        //true이면 flag를 true로 설정한다.
        if(isValid){
            flag = true;
            //유저 public profile 가져오기
            String userInfoApi = "https://graph.facebook.com/v3.3/me?fields=id%2Cname&access_token="+requestToken;
            String userInfoResult = this.requestAPI(userInfoApi, "GET", null);

            System.out.println(userInfoResult);
            JSONObject userInfoJson = new JSONObject(userInfoResult);

            String userId = userInfoJson.get("id").toString();
            String username = URLDecoder.decode(userInfoJson.get("name").toString(),"utf-8");
            tempUser = new Users().setUserId(userId).setUsername(username).setPassword("");
            System.out.println(tempUser);
        }

        return Optional.of(tempUser);
    }

    public void verifyNaverOauthToken(HttpServletResponse response
            , HttpSession session
            , String accessToken){
//        https://openapi.naver.com/v1/nid/me
        String requestUrl = "https://openapi.naver.com/v1/nid/me";
        Map<String, String> property = new HashMap<>();
        property.put("Authorization", "Bearer "+accessToken);

        String result = this.requestAPI(requestUrl, "GET", property);

        JSONObject resultJson = new JSONObject(result);
        JSONObject responseObject = (JSONObject) resultJson.get("response");

        String userId = responseObject.get("id").toString();
        String username = URLDecoder.decode(responseObject.get("name").toString());
        String email = responseObject.get("email").toString();

        //#1. 데이터 베이스에 있어 없어?
        Users currentUser = null;
        if(!usersRepository.findByUserId(userId).isPresent()){
            currentUser = new Users()
                    .setUserId(userId)
                    .setUsername(username)
                    .setPassword("")
                    .setUserType(userTypeRepository.findByTypeName("naver").get());
            //#2. 없으면 회원가입 처리 하기
            usersRepository.save(currentUser);
        }else{
            currentUser = usersRepository.findByUserId(userId).get();
        }
        //#3. 로그인 처리
        authService.createAuth(response, session, currentUser);
    }

    //
    public void verifyKakaoOauthToken(HttpServletResponse response
            ,HttpSession session
            , String accessToken){

        String requestUrl = "https://kapi.kakao.com/v2/user/me";
        Map<String, String> property = new HashMap<>();
        property.put("Authorization", "Bearer "+accessToken);

        String result = this.requestAPI(requestUrl, "GET", property);

        JSONObject responseObject = new JSONObject(result);

        String userId = responseObject.get("id").toString();
        JSONObject propertyJson = (JSONObject) responseObject.get("properties");
        String username = URLDecoder.decode(propertyJson.get("nickname").toString());
//      카카오 api로 부터 이메일 값이 안넘어 온다 이상한데?

        //#1. 데이터 베이스에 있어 없어?
        Users currentUser = null;
        if(!usersRepository.findByUserId(userId).isPresent()){


            currentUser = new Users()
                    .setUserId(userId)
                    .setUsername(username)
                    .setPassword("")
                    .setUserType(userTypeRepository.findByTypeName("kakao").get());


            //#2. 없으면 회원가입 처리 하기
            usersRepository.save(currentUser);
        }else{
            currentUser = usersRepository.findByUserId(userId).get();
        }
        //#3. 로그인 처리

        authService.createAuth(response, session, currentUser);
    }


    //request url
    public String requestAPI(String url, String method, Map<String, String> property){
        StringBuilder resultStringBuilder = new StringBuilder();

        try {
            URL debugUrl = new URL(url);
            HttpsURLConnection conn = (HttpsURLConnection) debugUrl.openConnection();


            if(property != null) {
                for (String key : property.keySet()) {
                    conn.setRequestProperty(key, property.get(key));
                }
            }


            conn.setRequestMethod(method);
            System.out.println("#RESPONSE CODE : "+conn.getResponseCode());
            InputStream is = conn.getInputStream();
            BufferedReader br2 = new BufferedReader(new InputStreamReader(is));

            while(true){
                String result = br2.readLine();

                if(result ==null){
                    break;
                }
                resultStringBuilder.append(result);

            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String validationResult = resultStringBuilder.toString();
        System.out.println("#requestAPI result ->"+validationResult);

        return validationResult;
    }
}
