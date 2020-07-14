package com.itheima.messageproducer;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;

@SpringBootApplication
// 在启动类上实现 CommandLineRunner 或 ApplicationRunner 接口，可以在启动类执行时执行业务逻辑
public class ProducerApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    // 注入 Rabbit 模板工具
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void run(String... args) throws Exception {
        // 如果想发送到自定义的交换机,需要使用RabbitTemplate
        // Rabbit 管理工具
        RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate);
        // 定义交换机
        String exchangeName = "exchange-hello";
        // 多态: Exchange 是一个接口,实现类有:DirectExchange、FanoutExchange、TopicExchange
        Exchange exchange = new DirectExchange(exchangeName);
        rabbitAdmin.declareExchange(exchange);
        // 定义队列
        String queueName = "hello-test";
        Queue queue = new Queue(queueName);
        rabbitAdmin.declareQueue(queue);
        /*
            定义绑定关系:
                参数解释:
                    destination --> 目的地(一般是队列名字,也可以是交换机)
                    destinationType --> 目的地类型(队列或交换机):DestinationType.QUEUE 或 DestinationType.EXCHANGE
                    exchange --> 交换机名字
                    routingKey --> 路由key
                    arguments --> 参数
         */
        Binding binging = new Binding(queueName, Binding.DestinationType.QUEUE, exchangeName, "hello", new HashMap<>());
        rabbitAdmin.declareBinding(binging);
        // 发送消息
        // 参数一:交换机名字; 参数一:路由key; 参数三:消息内容
        rabbitTemplate.convertAndSend(exchangeName, "hello", "hello rabbit");
    }

    /*// 注入 Amqp 模板工具
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public void run(String... args) throws Exception {
        // 程序启动后执行
        // 第一个参数为路由Key(routing key),第二个参数为要发送的消息内容
        amqpTemplate.convertAndSend("hello", "hello world...");
    }*/
}
