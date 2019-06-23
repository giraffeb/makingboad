package com.example.board.myauth;


import com.example.board.Users.UserTypeRepository;
import com.example.board.Users.Users;
import com.example.board.Users.UsersRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import io.jsonwebtoken.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.GeneralSecurityException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class AuthService {

    private static Logger logger = LoggerFactory.getLogger(AuthService.class);

    private UsersRepository usersRepository;
    private UserTypeRepository userTypeRepository;

    public AuthService(UsersRepository usersRepository, UserTypeRepository userTypeRepository) {
        this.usersRepository = usersRepository;
        this.userTypeRepository = userTypeRepository;
    }

    //TODO: jwt-> cookie and session 방식으로
    public boolean checkAuthentication(Users targetUser){

        Optional<Users> resultUser = usersRepository
                                .findByUserId(targetUser.getUserId())
                                .filter(returnUser-> returnUser.getPassword().equals(targetUser.getPassword()));

        logger.debug("loginCheck -> "+resultUser.toString());

        return resultUser.isPresent();
    }


    //create session and jwt

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

    public void parseJwt(String jwt){
        boolean flag = false;

        if(jwt.isEmpty() || jwt == null){
            return ;
        }

        Jws<Claims> parsedJwt = null;
        try {
            parsedJwt = Jwts.parser().setSigningKey("secret".getBytes("UTF-8")).parseClaimsJws(jwt);

            System.out.println("#signature -> "+parsedJwt.getSignature());
            System.out.println("#expiration -> "+parsedJwt.getBody().getExpiration());

            flag = true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ExpiredJwtException e){
            e.printStackTrace();
//            cookie.setValue("");
//            response.addCookie(cookie);
        }
    }

    public void createAuth(HttpServletResponse response
                            ,HttpSession session
                            ,Users currentUser){

        String jwt = null;
        try {
            jwt = this.createJwt();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.createCookie(response, jwt);
        this.createSession(session, currentUser);


    }

    public void createCookie(HttpServletResponse response, String jwt){

        Cookie myCookie = new Cookie("mymymy", jwt);
        myCookie.setHttpOnly(true);
        myCookie.setValue(jwt);

        response.setHeader("Authorization", "Bearer " + jwt);
        response.addCookie(myCookie);

    }

    public void createSession(HttpSession session, Users currentUser){

        session.setAttribute("userid", currentUser.getUserId());
        session.setAttribute("username", currentUser.getUsername());
    }


}
