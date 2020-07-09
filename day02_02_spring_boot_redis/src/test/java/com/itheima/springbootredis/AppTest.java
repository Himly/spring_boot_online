package com.itheima.springbootredis;

import com.itheima.springbootredis.test.TestA;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest // 启动时先加载XxxApplication类(如RedisApplication),然后在加载这个类
public class AppTest {

    @Autowired
    private TestA testA;

    // 编程测试用例的注解
    @Test
    public void testShowA() {
        testA.show();
    }
}
