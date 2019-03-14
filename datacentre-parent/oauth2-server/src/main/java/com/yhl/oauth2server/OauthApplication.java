package com.yhl.oauth2server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = {"com.yhl.**"})
public class OauthApplication {
    public static void main(String[] args) {

        SpringApplication.run(OauthApplication.class, args);
        System.out.println(new BCryptPasswordEncoder().encode("123456"))  ;
    }
}
