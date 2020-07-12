package com.itheima.helloserviceconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient // 开启客户端发现,注册中心不仅支持Eureka,还支持其他的注册中心如：Consul
// @EnableEurekaClient // 开启客户端发现,注册中心仅仅支持Eureka
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    // 在启动类中将 RestTemplate 注册成 Bean
    @Bean
    @LoadBalanced // 开启 Ribbon 负载均衡
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
