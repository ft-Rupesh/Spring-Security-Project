
package com.globex.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.globex.SpringApplicationContext;
import com.globex.model.LoginRequestModel;
import com.globex.model.UserDto;
import com.globex.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.Key;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

public class MyAuthenticationFilter extends UsernamePasswordAuthenticationFilter {





    public MyAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);


    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {


        LoginRequestModel creds = new ObjectMapper().readValue(req.getInputStream(), LoginRequestModel.class);
        return getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));


    }


    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication authResult) throws IOException, ServletException {
       String userEmail= ((UserPrincipal)authResult.getPrincipal()).getUsername();
       UserService userService=(UserService) SpringApplicationContext.getBean("userServiceImpl");
        UserDto userDto= userService
                .getUserDetailsBYEmail(userEmail);
        String commonKey=SecurityConstants.getSecretToken();
//        byte [] secretKeyInBytes= Base64.getEncoder().encode(commonKey.getBytes());

        SecretKey secretKey= Keys.hmacShaKeyFor(commonKey.getBytes());

        String token= Jwts.builder()
                .claim("roles",userDto.getRole())
                .subject(userDto.getUserId())
                .expiration(Date.from(Instant.now().plusMillis(Long.parseLong(SecurityConstants.getTokenExpirationTime()))))
                .issuedAt(Date.from(Instant.now()))
                .signWith(secretKey)
                .compact();//build the token
        res.addHeader(SecurityConstants.HEADER_STRING,SecurityConstants.TOKEN_PREFIX+token);
        res.addHeader(SecurityConstants.USER_ID,userDto.getUserId());// angular get the token
    }
}
