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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;

@Controller
public class AuthController {

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private AuthService authService;


    @GetMapping("/login")
    public String getLoginPage(HttpSession session) {
        if (session.getAttribute("username") != null) {
            return "redirect:/";
        }

        return "login";
    }

    @PostMapping("/login")
    public String getLoginPost(@Valid @ModelAttribute("users") UsersRequestDto usersRequestDto,
                               BindingResult result,
                               HttpSession session,
                               HttpServletResponse response,
                               Model model) throws UnsupportedEncodingException {

        System.out.println("#loginCall");

        String userid = usersRequestDto.getUserId();
        String username = usersRequestDto.getUsername();
        String password = usersRequestDto.getPassword();

        Users targetUsers = new Users().setUserId(userid).setUsername(username).setPassword(password);


        String resultPage = "login";

        if (authService.checkAuthentication(targetUsers)) {
            System.out.println("#token create");
            String jwt = Jwts.builder()
                    .setSubject("user")
                    .setExpiration(Date.valueOf(LocalDate.now().plusDays(1L)))
                    .claim("username", username)
                    .claim("scope", "this is scope")
                    .signWith(SignatureAlgorithm.HS256, "secret".getBytes("UTF-8"))
                    .compact();


            Cookie myCookie = new Cookie("mymymy", jwt);
            myCookie.setHttpOnly(true);
            myCookie.setValue(jwt);

            response.setHeader("Authorization", "Bearer " + jwt);
            response.addCookie(myCookie);

            resultPage = "redirect:/";
        }

        return resultPage;
    }


    @PostMapping("/snslogin")
    public String getSnsLogin(@RequestParam String idtoken,
                              @RequestParam String sns_type,
                              HttpServletResponse response,
                              HttpSession session) throws GeneralSecurityException, IOException {

        System.out.println("idtoken = "+idtoken);
        System.out.println("sns_type = "+sns_type);
        boolean flag = false;
        if(sns_type.equals("google")){
            System.out.println("Google Call");
            flag = authService.verifyGoogleOauthToken(idtoken);

        }else if(sns_type.equals("facebook")){
            flag = authService.verifiyingFacebookOauthToken(idtoken);

        }

        System.out.println("#FALG ->"+flag);

        if(flag == true){

            String jwt = authService.createJwt();

            Cookie myCookie = new Cookie("mymymy", jwt);
            myCookie.setHttpOnly(true);
            myCookie.setValue(jwt);

            response.setHeader("Authorization", "Bearer " + jwt);
            response.addCookie(myCookie);
        }

        return "redirect:/";
    }

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


    @GetMapping("/signup")
    public String getSignUpPage(Model model){
        return "signUp";
    }


    @PostMapping("/signup")
    public String signUpPost(@Valid @ModelAttribute("users") UsersRequestDto usersRequestDto,
                             BindingResult result,
                             Model model){

        if(result.hasErrors()){
            result.getFieldErrors().stream()
                    .forEach(e->model.addAttribute(e.getField(), e.getDefaultMessage()));

            result.getFieldErrors()
                    .stream()
                    .forEach((e)-> System.out.println("ERROR : "+e.getField()+" : "+e.getDefaultMessage()));


            return "signUp";
        }
        System.out.println("#users ->"+usersRequestDto.toEntity());
        userRepository.save(usersRequestDto.toEntity());

        return "redirect:/";
    }



}
