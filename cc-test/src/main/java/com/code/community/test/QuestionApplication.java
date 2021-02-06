package com.code.community.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan({"com.code.community.test.dao"})
@EnableCaching
public class QuestionApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuestionApplication.class,args);
    }
}
