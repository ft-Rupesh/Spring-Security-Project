package com.globex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.globex.security.AppProperties;

@SpringBootApplication(scanBasePackages = {"com.globex.controller", "com.globex.repository", "com.globex.service","com.globex.security","com.globex.model","com.globex.pojo","com.globex.userdto","com.globex.shared","com.globex.model.mapper"})
public class SpringBootCurdApp {

	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootCurdApp.class, args);
	}
	 @Bean
	    public BCryptPasswordEncoder bCryptPasswordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	    @Bean
	    public SpringApplicationContext springApplicationContext() {
	        return new SpringApplicationContext();
	    }

	    @Bean(value = "AppProperties")
	    public AppProperties appProperties() {
	        return new AppProperties();
	    }
}
