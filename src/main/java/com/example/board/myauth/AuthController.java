package com.example.board.myauth;


import com.example.board.Users.Users;
import com.example.board.Users.UsersRepository;
import com.example.board.Users.UsersRequestDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;


/***
 * 회원가입, 로그인, 로그아웃에 대한 처리를 담당하는 컨트롤러 입니다.
 *
 */
@Controller
public class AuthController {

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private ServletContext servletContext;

    private UsersRepository userRepository;
    private AuthService authService;
    private OauthService oauthService;

    public AuthController(UsersRepository userRepository, AuthService authService, OauthService oauthService) {
        this.userRepository = userRepository;
        this.authService = authService;
        this.oauthService = oauthService;
    }

    //로그인 페이지 요청
    @GetMapping("/login")
    public String getLoginPage(HttpSession session, Model model) throws UnsupportedEncodingException {
        if (session.getAttribute("username") != null) {
            return "redirect:/";
        }

        System.out.println("#CONTEXT PATH: "+servletContext.getContextPath());
        //네이버 로그인 api
        String clientId = "XxJkIuBkesXAxBPGPPSh";//애플리케이션 클라이언트 아이디값";
//        String redirectURI = URLEncoder.encode("http://localhost:8080/naver_callback", "UTF-8");
        String redirectURI = URLEncoder.encode("https://giraffeb.org/makingboard/naver_callback", "UTF-8");
        SecureRandom random = new SecureRandom();
        String state = new BigInteger(130, random).toString();
        String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
        apiURL += "&client_id=" + clientId;
        apiURL += "&redirect_uri=" + redirectURI;
        apiURL += "&state=" + state;
        session.setAttribute("state", state);

        model.addAttribute("naver_oauth", apiURL);


        //카카오 api
        ///oauth/authorize?client_id={app_key}&redirect_uri={redirect_uri}&response_type=code
        String kakaiAppKey = "694e2374c8021b29f8258627d13a2c0a";
//        String kakaoRedirectUrl = "http://localhost:8080/kakao_callback";
        String kakaoRedirectUrl = "https://giraffeb.org/makingboard/kakao_callback";
        String kakaoApiUrl = "https://kauth.kakao.com/oauth/authorize";
        kakaoApiUrl += "?client_id="+kakaiAppKey;
        kakaoApiUrl += "&redirect_uri="+kakaoRedirectUrl;
        kakaoApiUrl += "&response_type=code";
        apiURL += "&state=" + state;

        model.addAttribute("kakao_oauth", kakaoApiUrl);

        return "login";
    }

    //아이디 패스워드 로그인
    @PostMapping("/login")
    public String getLoginPost(@Valid @ModelAttribute("users") UsersRequestDto usersRequestDto,
                               BindingResult result,
                               HttpSession session,
                               HttpServletResponse response,
                               Model model) throws UnsupportedEncodingException {

        //로그인 절차를 생각해보자
        //#1-1. 아이디, 패스워드를 전송한다.
        //#1-1-1. 해당 아이디 패스워드가 형식에 맡는지 확인한다.
        //#1-1-2. 해당 아이디 패스워드를 데이터베이스에서 확인한다.
        //#1-1-3. 위의 과정이 성공적이라면, 토큰을 발급하고 쿠키에 저장하고, 세션에 유저정보를 저장한다.

        //#1-2. 소셜 로그인을 요청한다.
        //#1-2-1. 유저에게서 받은 엑세스 토큰 또는 아이디 토큰이 적합한지 인증한다.
        //#1-2-2. 위의 과정이 성공적이라면, 토큰을 발급하고 쿠키에 저장하고, 세션에 유저정보를 저장한다.




        System.out.println("#loginCall");

        String userid = usersRequestDto.getUserId();
        String username = usersRequestDto.getUsername();
        String password = usersRequestDto.getPassword();

        Users targetUsers = new Users().setUserId(userid).setUsername(username).setPassword(password);


        String resultPage = "login";

        if (authService.checkAuthentication(targetUsers)) {
            System.out.println("#token create");
            authService.createAuth(response, session, targetUsers);

            resultPage = "redirect:/";
        }

        return resultPage;
    }

    //snslogin & signup
    //소셜 로그인은 동시에 처리하도록 한다.
    @PostMapping("/snslogin")
    public String getSnsLogin(@RequestParam String idtoken,
                              @RequestParam String sns_type,
                              HttpServletResponse response,
                              HttpSession session) throws GeneralSecurityException, IOException {

        //#1-2. 소셜 로그인을 요청한다.
        //#1-2-1. 유저에게서 받은 엑세스 토큰 또는 아이디 토큰이 적합한지 인증한다.
        //#1-2-2. 가입여부도 확인하며, 미 가입시 가입처리를 자동적으로 처리한다.
        //#1-2-3. 위의 과정이 성공적이라면, 토큰을 발급하고 쿠키에 저장하고, 세션에 유저정보를 저장한다.

        //토큰의 적합성 여부를 저장할 값.
        Users tempUser = null;

        //#1-2-1. 유저에게서 받은 액세스 토큰 또는 아이디 토큰이 적합한지 인증한다.
        //소셜 로그인 플랫폼 별로 로그인 방법을 나눔.
        //각각의 방법으로 사용자 토큰을 인증한다.
        if(sns_type.equals("google")){
            System.out.println("Google Call");
            tempUser = oauthService.verifyGoogleOauthToken(idtoken).orElse(null);

        }else if(sns_type.equals("facebook")){
            tempUser = oauthService.verifiyFacebookOauthToken(idtoken).orElse(null);
        }


        //디비에 있는지 확인하기.
        Users myUser = null;
        if(tempUser != null){ //null이라면 valid 실
            myUser = userRepository.findByUserId(tempUser.getUserId()).orElse(null);
        }

        //없으면 만든다.
        if(myUser == null){
            myUser = tempUser;
            userRepository.save(myUser);
        }

        //사용자 토큰이 인증되었다면
        //사이트 로그인에 사용할 JWT토큰을 발급한다.
        String jwt = authService.createJwt();
        authService.createCookie(response, jwt);
        authService.createSession(session, myUser);
        return "redirect:/";
    }

    //로그아웃 처리.
    //쿠키와 세션을 제거합니다.
    @PostMapping("/logout")
    public String getLogout(@RequestParam Map<String, Object> params,
                            HttpServletRequest request,
                            HttpSession session,
                            HttpServletResponse response){
        session.removeAttribute("username");

        Cookie myCookie = new Cookie("mymymy","");
        myCookie.setMaxAge(0);

        response.addCookie(myCookie);
        return "redirect:/";
    }

    //회원가입 페이지 처리
    @GetMapping("/signup")
    public String getSignUpPage(Model model){
        return "signUp";
    }

    //일반회원가입 처리.
    @PostMapping("/signup")
    public String signUpPost(@Valid @ModelAttribute("users") UsersRequestDto usersRequestDto,
                             BindingResult bindingResult,
                             Model model){
        //회원가입 절차를 생각해보자.
        //#1. 회원가입시 입력한 값이 적절한 형태인지 확인한다.
        //#2. 해당 정보가 이미 중복되지 않았는지 확인한다.
        //#3. 1,2번을 모두 통과했다면, 데이터베이스에 저장한다.


        //#1. 값의 형태가 검증되지 않았다면, 에러를 반환한다.
        if(bindingResult.hasErrors()){
            bindingResult.getFieldErrors().stream()
                    .forEach(e->model.addAttribute(e.getField(), e.getDefaultMessage()));

            bindingResult.getFieldErrors().stream()
                    .forEach((e)-> System.out.println("ERROR : "+e.getField()+" : "+e.getDefaultMessage()));


            return "signUp";
        }

        //#2. 데이터 베이스에 있는지 확인한다.
        String userId = usersRequestDto.getUserId();
        Optional<Users> user = userRepository.findByUserId(userId);

        //#3-1. 이미 해당 아이디의 사용자가 존재한다면, 가입거부 에러 출력
        //#3-2. 해당 아이디가 가입 가능하다면 유저객체로 디비에 저장합니다.
        user.ifPresentOrElse(
                (currentUser)->{ model.addAttribute(bindingResult.getFieldError("userId").getField(), "해당 유저가 이미 존재합니다."); }
                ,()-> userRepository.save(usersRequestDto.toEntity()) );

        logger.debug(usersRequestDto.toEntity().toString());

        return "redirect:/";
    }


}
