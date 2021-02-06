package com.code.community.oss;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan({"com.code.community.oss.dao"})
@EnableCaching
@EnableTransactionManagement
public class MinioOSSApplication {
    public static void main(String[] args) {
        SpringApplication.run(MinioOSSApplication.class,args);
    }
}
