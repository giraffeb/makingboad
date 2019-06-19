package com.example.board.myauth;


import com.example.board.Users.Users;
import com.example.board.Users.UsersRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.Cookie;
import java.io.*;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

@Component
public class AuthService {

    private static Logger logger = LoggerFactory.getLogger(AuthService.class);

    private UsersRepository usersRepository;

    public AuthService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    //TODO: session -> jwt 변경
    public boolean checkAuthentication(Users targetUser){

        System.out.println("#authService->"+targetUser);
        Optional<Users> resultUser = usersRepository
                                .findByUserId(targetUser.getUserId())
                                .filter(returnUser-> returnUser.getPassword().equals(targetUser.getPassword()));

        logger.debug("loginCheck -> "+resultUser.toString());

        return resultUser.isPresent();
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

    public boolean verifyGoogleOauthToken(String idTokenString) throws GeneralSecurityException, IOException {

        boolean flag = false;

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

            flag = true;


        } else {
            System.out.println("Invalid ID token.");
        }

        return flag;
    }


    public boolean verifiyingFacebookOauthToken(String requestToken) throws IOException {
        boolean flag = false;
        //facebook은 requestToken를 받아서 인증에 사용합시다.
        System.out.println("requestToken: "+requestToken);

        //TODO: 외부 설정파일로 변경해야 될 것 같습니다.
        String clientID = "2208086505911469";
        String clientSecret = "f0be472a841d6b5a7ee9a5b0e78f3693";

        //Client App AccessToken을 가져오는 api 입니다.
        //client_id : 클라이언트 아이디
        //client_secret : 클라이언트 시크릿.
        URL appAccessTokenRequestUrl = new URL("https://graph.facebook.com/oauth/access_token?client_id="+clientID+"&client_secret="+clientSecret+"&grant_type=client_credentials");
        HttpsURLConnection conn = (HttpsURLConnection) appAccessTokenRequestUrl.openConnection();
        conn.setRequestMethod("GET");

        InputStream is = conn.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        while(true){
            String result = br.readLine();

            if(result ==null){
                break;
            }
            sb.append(result);

        }
        String result2 = sb.toString();
        System.out.println(result2);

        JSONObject appAccessTokenJson = new JSONObject(result2);
        System.out.println(appAccessTokenJson.get("access_token"));

        String appAccessToken = appAccessTokenJson.get("access_token").toString();


        //사용자가 전송한 requestToken을 검증하는 api
        //input_token : 사용자가 전송한 토큰
        //access_token : 클라이언트 엑세스 토큰.
        URL debugUrl = new URL("https://graph.facebook.com/debug_token?input_token="+requestToken+"&access_token="+appAccessToken);
        HttpsURLConnection conn2 = (HttpsURLConnection) debugUrl.openConnection();
        conn2.setRequestMethod("GET");

        InputStream is2 = conn2.getInputStream();
        BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
        StringBuilder sb2 = new StringBuilder();

        while(true){
            String result = br2.readLine();

            if(result ==null){
                break;
            }
            sb2.append(result);

        }

        String validationResult = sb2.toString();
        System.out.println(validationResult);

        JSONObject validJson = new JSONObject(validationResult);
        System.out.println(((JSONObject)validJson.get("data")).get("is_valid"));

        boolean isValid = Boolean.valueOf(((JSONObject)validJson.get("data")).get("is_valid").toString());

        //사용자가 보낸 AccessToken 검증결과
        //true이면 flag를 true로 설정한다.
        if(isValid){
            flag = true;
        }

        return flag;
    }

    public String createJwt() throws UnsupportedEncodingException {

        //TODO: userID값, 사용자이름과 이메일을 자체적으로 저장하자.
        //불어올 수 있어야 함.
        String jwt = Jwts.builder()
                .setSubject("user")
                .setExpiration(Date.valueOf(LocalDate.now().plusDays(1L)))
                .claim("username", "testuser")
                .claim("scope", "this is scope")
                .signWith(SignatureAlgorithm.HS256, "secret".getBytes("UTF-8"))
                .compact();

        return jwt;
    }
}
