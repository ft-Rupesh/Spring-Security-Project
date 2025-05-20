package com.globex.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.globex.repository.UserRepository;
import com.globex.service.UserService;

@EnableMethodSecurity(prePostEnabled = true,securedEnabled = true)
@Configuration
@EnableWebSecurity
public class MyWebSecurity {
@Autowired
private UserService userService;

@Autowired
    UserRepository userRepository;

@Autowired
private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder=
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
        AuthenticationManager authenticationManager= authenticationManagerBuilder.build();


        http
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(authz->authz
                        .requestMatchers("/users/**")
                        .permitAll()
                        .requestMatchers("/todo/**").permitAll()
                        .requestMatchers("/users/delete/**").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated())
                .addFilter(new MyAuthenticationFilter(authenticationManager))
                .addFilter(new AuthorizationFilter(authenticationManager,userRepository))
                .authenticationManager(authenticationManager)
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

http.headers(headers->headers.frameOptions(frameOptions->frameOptions.sameOrigin()));

        return http.build();
    }
}
