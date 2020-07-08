package com.itheima.springbootssm.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration // 配置类
/*
    包扫描：相当于
        <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
            <property name="basePackage" value="cn.itcast.dao"></property>
        </bean>
 */
@MapperScan("com.itheima.springbootssm.mapper")
public class MyBatisConfig {

}
