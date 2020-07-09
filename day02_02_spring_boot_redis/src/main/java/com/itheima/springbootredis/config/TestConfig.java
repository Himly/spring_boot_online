package com.itheima.springbootredis.config;

import com.itheima.springbootredis.test.MyImportSelector;
import com.itheima.springbootredis.test.TestA;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
// @Import({TestA.class}) // TestA 交给 Spring IOC 容器管理方式二
@Import(MyImportSelector.class) // TestA 交给 Spring IOC 容器管理方式三
public class TestConfig {

    // ------------------- TestA 交给 Spring IOC 容器管理方式一 start
    /*@Bean // 将 TestA 实例化后交给 Spring 的 IOC 容器进行管理,在其他地方就可以直接注入使用
    public TestA testA() {
        return new TestA();
    }*/
    // ------------------- TestA 交给 IOC 容器管理方式一 end
}
