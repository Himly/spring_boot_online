package com.itheima.springbootdemo.config;

import com.itheima.springbootdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration // 声明这个类是一个配置类
// @PropertySource("classpath:user.properties") // 加载配置文件
public class UserConfig {
    // 获取数据的方式一：通过 @Value 注解
    /*@Value("${user.username}") //使用@Value注解获取值
    private String username;

    @Value("${user.passwork}") //使用@Value注解获取值
    private String password;

    @Value("${user.age}") //使用@Value注解获取值
    private Integer age;

    @Bean // 创建User对象并交给spring的ioc容器,User对象中的值从配置文件中获取
    public User getUser() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }*/

    // 获取数据的方式二：Environment 获取数据
    /*@Autowired // 注入环境变量 environment
    private Environment environment;

    @Bean // 创建User对象并交给spring的ioc容器,User对象中的值从配置文件中获取
    public User getUser() {
        User user = new User();
        user.setUsername(environment.getProperty("user.username"));
        user.setPassword(environment.getProperty("user.passwork"));
        user.setAge(Integer.parseInt(environment.getProperty("user.age")));
        return user;
    }*/

    // 获取数据的方式三：@ConfigurationProperties 注解
    @Bean // 创建User对象并交给spring的ioc容器,User对象中的值从配置文件中获取
    @ConfigurationProperties("user") // user.username 等的前缀 user
    public User getUser() {
        User user = new User();
        return user;
    }

}
