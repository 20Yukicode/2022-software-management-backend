package com.campus.love.tweet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.campus.love")
@EnableFeignClients(basePackages = "com.campus.love.common.feign")
public class TweetServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TweetServiceApplication.class,args);
    }
}
