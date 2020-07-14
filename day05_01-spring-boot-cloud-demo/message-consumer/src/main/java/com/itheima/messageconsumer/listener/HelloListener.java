package com.itheima.messageconsumer.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class HelloListener {
    // 设置监听器,监听队列
    @RabbitListener(queues = {"hello"})
    public void handleMessage(String message) {
        System.out.println(message);
    }
}
