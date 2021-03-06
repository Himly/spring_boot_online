package com.itheima.helloserviceprovider.controller;

import com.itheima.helloserviceprovider.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/hello", name = "hello 服务模块")
@Slf4j // 使用日志
public class HelloController {

    @GetMapping
    public String get() {
        // 打印日志
        log.info("get方法被调用了..." + System.currentTimeMillis());
        // 为了看到页面编号,返回值加上当前时间
        return "hello spring cloud..." + System.currentTimeMillis();
    }

    /*// 通过表单形式提交
    @PostMapping(value = "/postParam", name = "表单提交功能")
    public String postParam(@RequestParam Map<String, String> map) {
        String name = map.get("name");
        Integer age = Integer.parseInt(map.get("age"));
        System.out.println(name + "..." + age);
        return "hello " + name + "..." + System.currentTimeMillis();
    }*/

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
