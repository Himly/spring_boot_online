package com.itheima.helloconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    // 在启动类中将 RestTemplate 注册成 Bean
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
