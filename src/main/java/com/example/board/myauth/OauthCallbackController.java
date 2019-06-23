package com.example.board.myauth;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/***
 * 서버사이드 oauth callback처리를 하는 컨트롤러 입니다.
 * 각각의 api마다 리턴되는 정보나 방식이 조금씩은 다를 수 있으므로 구현을 달리 해야합니다.
 * 네이버와 카카오는 서버사이드로 값을 가져오고, 구글과 페이스북은 클라이언트 사이드에서 액세스토큰 또는 아이디 토큰을 서버로 보내서 값을 검증합니다.
 */
@Controller
public class OauthCallbackController {


    private AuthService authService;
    private OauthService oauthService;

    public OauthCallbackController(AuthService authService, OauthService oauthService) {
        this.authService = authService;
        this.oauthService = oauthService;
    }

    @GetMapping("/naver_callback")
    public String test(HttpServletRequest request
            , HttpServletResponse response
            , HttpSession session) throws UnsupportedEncodingException {

        /*
        * #1. 로그인 페이지에서 sns 서버로 권한을 요청하는 링크를 실행하고,
        * #2. 해당 서버에서 리다이렉트로 콜백주소를 보내고 이 메소드들이 호출됩니다.
        * #3. 이 콜백이 호출될때 인증코드를 파라미터로 함께 받게 되고 이 값을 사용해서 액세스 토큰 값을 얻게됩니다.
        * #4. 액세스 토큰을 사용해서 사용자의 프로파일에 필요한 정보를 요청해서 로그인 처리를 하게 됩니다.
        * */


        //#액세스 토큰 요청
        String clientId = "XxJkIuBkesXAxBPGPPSh";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "xOnt6jkH7J";//애플리케이션 클라이언트 시크릿값";
        String code = request.getParameter("code");
        String state = request.getParameter("state");
//        String redirectURI = URLEncoder.encode("http://localhost:8080/naver_callback", "UTF-8");
        String redirectURI = URLEncoder.encode("https://giraffeb.org/makingboard/naver_callback", "UTF-8");

        String apiURL;
        apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
        apiURL += "client_id=" + clientId;
        apiURL += "&client_secret=" + clientSecret;
        apiURL += "&redirect_uri=" + redirectURI;
        apiURL += "&code=" + code;
        apiURL += "&state=" + state;

        String resultString = oauthService.requestAPI(apiURL, "GET", null);
        JSONObject result = new JSONObject(resultString);

        //액세스 토큰을 이용해서 회원정보를 요청하고 그 값을 이용해서 jwttoken을 발급하고 로그인처리를 합니다.
        oauthService.verifyNaverOauthToken(response, session, result.get("access_token").toString());
        System.out.println("complete naver oauth");
        return "redirect:/";
    }

    @GetMapping("/kakao_callback")
    public String kakaoCallback(HttpServletRequest request
                                , HttpServletResponse response
                                , HttpSession session
                                , ServletContext context) throws IOException {
        System.out.println("kakao_callback call");

        //#1. request auth code

        //#2. request access token
//        https://kauth.kakao.com//oauth/token/
        String kakaoAppKey = "694e2374c8021b29f8258627d13a2c0a";
//        String redirectUri = URLEncoder.encode("https://giraffeb.org//kakao_callback", "utf-8");
        System.out.println("#CONTEXT PATH : "+context.getContextPath());
        String redirectUri = URLEncoder.encode("https://giraffeb.org/makingboard/kakao_callback", "utf-8");
        String accessTokenUrl = "https://kauth.kakao.com/oauth/token?";
        String authCode = request.getParameter("code");
        System.out.println("authcode->"+authCode);


        accessTokenUrl += "grant_type=authorization_code";
        accessTokenUrl += "&client_id="+kakaoAppKey;
        accessTokenUrl += "&redirect_uri="+redirectUri;
        accessTokenUrl += "&code="+authCode;

        String resultString = oauthService.requestAPI(accessTokenUrl, "POST", null);
        JSONObject result = new JSONObject(resultString);

        oauthService.verifyKakaoOauthToken(response, session, result.get("access_token").toString());
        
        return "redirect:/";
    }
}
