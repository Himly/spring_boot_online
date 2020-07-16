package com.itheima.lucenedemo.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.itheima.lucenedemo.mapper")
public class MybatisConfig {

}
