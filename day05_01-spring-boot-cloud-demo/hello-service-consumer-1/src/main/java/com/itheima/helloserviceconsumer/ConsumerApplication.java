package com.itheima.helloserviceconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

// @SpringBootApplication
// @EnableDiscoveryClient // 开启客户端发现,注册中心不仅支持Eureka,还支持其他的注册中心如：Consul
// @EnableCircuitBreaker
@SpringCloudApplication // = @SpringBootApplication + @EnableDiscoveryClient + @EnableCircuitBreaker
@EnableFeignClients // 开启 Feign 客户端
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    // Feign 中已经自动集成了 Ribbon 负载均衡，因此不需要自己定义 RestTemplate 了，可以注释所有使用 RestTemplate 的地方
    // 讲 RestTemplate 定义为 bean
    /*@Bean
    @LoadBalanced // 开启 Ribbon 负载均衡
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }*/
}
