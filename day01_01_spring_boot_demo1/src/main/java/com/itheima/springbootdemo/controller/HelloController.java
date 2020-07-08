package com.itheima.springbootdemo.controller;

import com.itheima.springbootdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    @RestController = @Controller + @ResponseBody
        @ResponseBody,用于将controller方法返回的对象通过转换器转换为指定的格式(通常为json)之后,写入到response对象的响应体中
        总结: 注解如果是异步交互并且只返回了数据而没有做页面跳转(转发或者重定向),都需要打这个注解
 */
@RestController
public class HelloController {

    @Autowired
    private User user;

    @GetMapping("/user")
    public User getUser() {
        return user;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello,Spring Boot!";
    }
}
