package com.itheima.helloserviceconsumer.controller;

import com.itheima.helloserviceconsumer.client.HelloClient;
import com.itheima.helloserviceconsumer.entity.User;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/consumer", name = "hello 消费模块")
// Feign 客户端中集成了 Hystrix, 默认是关闭的,打开之后需要先将之前定义的 Hystrix 注解命令都注释
// @DefaultProperties(defaultFallback = "defaultFallback") // 打了这个注解,默认该类下所有方法的降级处理方法都为defaultFallback()
public class ConsumerController {

    // 注入 RestTemplate: 在代码中发送 http 请求
    // @Autowired
    // private RestTemplate restTemplate;

    // 注入 HelloClient
    @Autowired
    private HelloClient helloClient;

    // 测试 get 请求(Ribbon 自动负载均衡)
    @GetMapping(name = "测试 get 请求")
    // @HystrixCommand(fallbackMethod = "helloFallback") // Hystrix服务降级命令,定义熔断注解,指定降级处理方法
    // @HystrixCommand // 因为在类上打了 @DefaultProperties(defaultFallback = "defaultFallback"),所以方法上不用再指定降级处理方法
    public String get() {
        // 在usl中直接使用服务id
        // String url = "http://hello-service-provider";
        // Feign 中已经自动集成了 Ribbon 负载均衡，因此不需要自己定义 RestTemplate 了，可以注释所有使用 RestTemplate 的地方
        // String result = restTemplate.getForObject(url + "/hello", String.class);
        String result = helloClient.hello();
        return result;
    }

    // 降级逻辑处理方法,需要注意的是: 调用这个方法的原始方法需要该方法保持参数类型和返回类型都一致
    public String helloFallback() {
        return "服务暂时不可用...";
    }

    @GetMapping(value = "/{id}", name = "get 请求测试服务熔断")
    @HystrixCommand(fallbackMethod = "getFallback") // 定义降级命令,指定降级方法
    public String get1(@PathVariable Integer id) {
        if (id == 1) {
            throw new RuntimeException("服务太忙了...");
        }
        return "服务正常调用...";
    }

    // 降级逻辑处理方法,需要注意的是: 调用这个方法的原始方法需要该方法保持参数类型和返回类型都一致
    public String getFallback(Integer id) {
        return "服务器太忙了,没法正常调用...";
    }

    // 测试 post 表单提交
    @GetMapping(value = "/postParam", name = "测试 post 表单提交功能")
    public String postParam() {
        String result = helloClient.postParam("tom");
        return result;
    }

    // 测试 post 请求体提交
    @GetMapping(value = "/postBody", name = "测试 post 请求体提交功能")
    public String postBody() {

        // 创建请求的实体对象,将请求体参数封装到对象的请求体中
        User user = new User("jerry", 19);
        String result = helloClient.postBody(user);
        return result;
    }

    // 默认的降级处理方法,配合类上的 @DefaultProperties(defaultFallback = "defaultFallback") 注解
    public String defaultFallback() {
        return "服务暂时不可用..." + System.currentTimeMillis();
    }

}
