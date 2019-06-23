package com.example.board.myauth;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


@Component
public class AuthInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();

        boolean flag = false;

        if(cookies != null){
            for(Cookie cookie :  cookies){
                if(cookie.getName().equals("mymymy")){
                    String jwt = cookie.getValue();

                    if(jwt.isEmpty() || jwt == null){
                        break;
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
                        cookie.setValue("");
                        response.addCookie(cookie);
                    }


                }
            }

        }


        if(flag == false){
            response.sendRedirect("login");
        }

//        //TODO: session -> jwt
//        if(session.getAttribute("username") == null){
//            response.sendRedirect("login");
//        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
