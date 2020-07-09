package com.itheima.hellospringboot.config;

import com.itheima.hellospringboot.util.HelloTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloAutoConfiguration {

    @Bean // 实例化 HelloTemplate 对象,并交给 Spring IOC 容器管理,这样我们就可以在任意位置使用@Autowired自动注入
    public HelloTemplate helloTemplate() {
        return new HelloTemplate();
    }
}
