package com.itheima.helloserviceconsumer.client;

import com.itheima.helloserviceconsumer.client.impl.HelloClientFallback;
import com.itheima.helloserviceconsumer.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

// @FeignClient("hello-service-provider") // 声明这是一个 Feign 客户端,并指定对应的服务名称
// 因为Feign 客户端中集成了 Hystrix, 默认是关闭的,打开之后需要从新设置 @FeignClient 注解
@FeignClient(value = "hello-service-provider", fallback = HelloClientFallback.class)
public interface HelloClient {
    // 需要指定具体的调用方法
    @GetMapping("/hello")
    String hello();

    // 需要注意使用 @RequestParam 指定传入的key
    @PostMapping("/hello/postParam")
    String postParam(@RequestParam("name") String name);

    @PostMapping("/hello/postBody")
    String postBody(@RequestBody User user);
}
