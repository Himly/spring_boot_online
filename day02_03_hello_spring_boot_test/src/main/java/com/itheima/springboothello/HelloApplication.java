package com.itheima.springboothello;

import com.itheima.hellospringboot.util.HelloTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(HelloApplication.class, args);
    }

    // 注入自定义模板工具
    @Autowired
    private HelloTemplate helloTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(helloTemplate.sayHello());
    }
}
