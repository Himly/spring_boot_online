package com.itheima.helloserviceprovider.controller;

import com.itheima.helloserviceprovider.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/hello", name = "hello 服务模块")
@Slf4j // 使用日志
@RefreshScope // 开启后本类中注入的属性如username即可动态刷新
public class HelloController {

    // 注入 user.name
    @Value("${user.name}")
    private String username;

    @GetMapping("/getName")
    public String getName() {
        return username + ", 么么哒..." + System.currentTimeMillis();
    }

    @GetMapping
    public String get() throws InterruptedException {
        // 打印日志
        log.info("get方法被调用了..." + System.currentTimeMillis());
        // 模拟超时
        // Thread.sleep(3000);
        // 为了看到页面编号,返回值加上当前时间
        return "hello spring cloud..." + System.currentTimeMillis();
    }

    // 通过表单形式提交
    @PostMapping(value = "/postParam", name = "post 表单提交功能")
    public String postParam(@RequestParam String name) {
        return "hello " + name + "..." + System.currentTimeMillis();
    }


    // 通过请求体提交
    @PostMapping(value = "/postBody", name = "post 请求体提交功能")
    public String postBody(@RequestBody User user) {
        return "hello " + user.getName() + " " + user.getAge() + "..." + System.currentTimeMillis();
    }
}
