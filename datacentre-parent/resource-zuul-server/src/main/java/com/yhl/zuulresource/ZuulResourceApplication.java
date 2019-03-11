package com.yhl.zuulresource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableZuulProxy
@ComponentScan(basePackages = {"com.yhl.**"})
public class ZuulResourceApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(ZuulResourceApplication.class, args);
	}
}
