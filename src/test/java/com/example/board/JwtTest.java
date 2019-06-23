package com.example.board;

import io.jsonwebtoken.*;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
public class JwtTest {


    @Test
    public void Jwt생성하기() throws UnsupportedEncodingException {
        //TODO: userID값, 사용자이름과 이메일을 자체적으로 저장하자.
        //불어올 수 있어야 함.
        String jwt = Jwts.builder()
                .setSubject("user")
                .setExpiration(Date.valueOf(LocalDate.now().plusDays(1L)))
                .claim("username", "testuser")
                .claim("scope", "this is scope")
                .signWith(SignatureAlgorithm.HS256, "secret".getBytes("UTF-8"))
                .compact();

        System.out.println(jwt);
    }

    @Test
    public void Jwt정보해석하기() throws UnsupportedEncodingException {
        boolean flag = false;

        String jwt = Jwts.builder()
                .setSubject("user")
                .setExpiration(Date.valueOf(LocalDate.now().plusDays(1L)))
                .claim("username", "testuser")
                .claim("scope", "this is scope")
                .signWith(SignatureAlgorithm.HS256, "secret".getBytes("UTF-8"))
                .compact();


        if(jwt.isEmpty() || jwt == null){
            return ;
        }

        Jws<Claims> parsedJwt = null;

        try {
            parsedJwt = Jwts.parser().setSigningKey("secret".getBytes("UTF-8")).parseClaimsJws(jwt);

            System.out.println("#signature -> "+parsedJwt.getSignature());
            System.out.println("#expiration -> "+parsedJwt.getBody().getExpiration());
            System.out.println("#body-> "+parsedJwt.getBody());

            JSONObject json = new JSONObject(parsedJwt.getBody());
            System.out.println(json.get("username"));

            flag = true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ExpiredJwtException e){
            e.printStackTrace();
//            cookie.setValue("");
//            response.addCookie(cookie);
        }
    }
}
