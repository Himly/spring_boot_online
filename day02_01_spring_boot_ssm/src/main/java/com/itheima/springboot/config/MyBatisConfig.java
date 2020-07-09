package com.itheima.springboot.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration // 配置类
@MapperScan("com.itheima.springboot.mapper")
public class MyBatisConfig {
}
