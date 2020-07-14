package com.itheima.helloserviceconsumer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String test() throws Exception {
        String result = "";
        // 新建一个线程池
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        // 使用线程池创建任务
        // new Callable<String>() {} 这里的String是回掉任务返回结果的类型
        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                // 模拟子线程执行耗时
                Thread.sleep(3000);
                return "Tom 准备完毕...";
            }
        });

        // 主线程中可以设置最长的等待时间
        Thread.sleep(1000);
        // 判断子线程是否执行完成
        if (future.isDone()) {
            result = future.get();
        } else {
            // 如果没有完成,返回预先设定好的值,PlanB
            result = "等不及了,换 Jerry 上吧...";
        }
        return result;
    }
}
