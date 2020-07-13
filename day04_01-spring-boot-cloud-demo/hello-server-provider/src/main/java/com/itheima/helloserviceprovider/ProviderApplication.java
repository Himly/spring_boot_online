package com.itheima.helloserviceprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient // 开启客户端发现,注册中心不仅支持EurekaServer,还支持其他的注册中心如：Consul
// @EnableEurekaClient // 开启客户端发现,注册中心仅仅支持EurekaServer
public class ProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}
