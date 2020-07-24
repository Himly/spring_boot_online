package com.itheima.vuedemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.itheima.vuedemo.mapper")
public class VueSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(VueSpringApplication.class, args);
    }
}
