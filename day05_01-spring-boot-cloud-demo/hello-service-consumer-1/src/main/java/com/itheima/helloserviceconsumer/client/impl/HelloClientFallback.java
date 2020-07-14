package com.itheima.helloserviceconsumer.client.impl;

import com.itheima.helloserviceconsumer.client.HelloClient;
import com.itheima.helloserviceconsumer.entity.User;
import org.springframework.stereotype.Service;

@Service // 这里使用 @Component 和 @Service 注解都行
public class HelloClientFallback implements HelloClient {
    @Override
    public String hello() {
        return "服务太忙了,请稍后重试..." + System.currentTimeMillis();
    }

    @Override
    public String postParam(String name) {
        return "服务太忙了,请稍后重试..." + System.currentTimeMillis();
    }

    @Override
    public String postBody(User user) {
        return "服务太忙了,请稍后重试..." + System.currentTimeMillis();
    }
}
