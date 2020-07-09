package com.itheima.springbootredis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;


/*
    开发小技巧：
        如果说我只需要做一个简单的 redis 测试或其他测试,没有必要去搭一个web界面,我可以直接在这个类中进行,只需要实现一个 ApplicationRunner 接口即可
 */
@SpringBootApplication
public class RedisApplication implements ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }

    // ======================= 方式一 start
    /*// 注入 redis 操作工具模板
    @Autowired
    private StringRedisTemplate redisTemplate;

    // 重写的 run 方法在应用启动后会执行
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 操作 redis 设置 key 和 value
        redisTemplate.opsForValue().set("name", "古力娜扎");
        // 获取键值对
        String name = redisTemplate.opsForValue().get("name");
        System.out.println(name);
    }*/
    // ======================= 方式一 end

    // ======================= 方式二 start
    // 设置 redis的 key:value
    // 企业中 Redis 一般都是由运维人员统一搭建的集群,多个部门同事使用redis,需要区分Redis中的key是有哪个部门使用的
    // 一般Redis的key都需要定义一个前缀, 格式：项目:模块
    // 注入 redis 操作工具模板
    public static String REDIS_PREFIX = "dp25:redis:user:";

    // 注入 redis 操作工具模板
    @Autowired
    private StringRedisTemplate redisTemplate;

    // 重写的 run 方法在应用启动后会执行
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 操作 redis 设置 key 和 value
        String key = REDIS_PREFIX + "name";
        String value = "易烊千玺";
        redisTemplate.opsForValue().set(key, value);
        // 获取键值对
        String name = redisTemplate.opsForValue().get(key);
        System.out.println(name);
    }
    // ======================= 方式二 end

}
