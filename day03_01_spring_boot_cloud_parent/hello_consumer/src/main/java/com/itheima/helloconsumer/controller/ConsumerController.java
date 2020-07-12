package com.itheima.helloconsumer.controller;

import com.itheima.helloconsumer.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/consumer", name = "hello 消费模块")
public class ConsumerController {

    // 注入 RestTemplate
    @Autowired
    private RestTemplate restTemplate;

    // 调用地址
    private String url = "http://localhost:8001/hello";

    // 测试 get 请求
    @GetMapping(name = "get 请求")
    public String get() {
        // restTemplate.getForObject 第一个参数是请求地址,第一个参数是返回值的类型
        String result = restTemplate.getForObject(url, String.class);
        return result;
    }

    // 测试 post 表单提交
    @GetMapping(value = "/postParam", name = "测试 post 表单提交功能")
    public String postParam() {
        // 可以通过 MultiValueMap 传递表单参数,但它是个接口,用它的实现类多态接收
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        // 添加参数
        map.add("name", "tom");
        map.add("age", "18");
        String result = restTemplate.postForObject(url + "/postParam", map, String.class);
        return result;
    }

    // 测试 post 表单提交
    @GetMapping(value = "/postBody", name = "测试 post 请求体提交功能")
    public String postBody() {
        User user = new User("jerry", 19);
        // 创建请求的实体对象,将请求体参数封装到对象的请求体中
        HttpEntity<User> httpEntity = new HttpEntity<>(user);
        String result = restTemplate.postForObject(url + "/postBody", httpEntity, String.class);
        return result;
    }

}
