package com.itheima.helloserviceconsumer.controller;

import com.itheima.helloserviceconsumer.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/consumer", name = "hello 消费模块")
public class ConsumerController {

    // 注入 RestTemplate
    @Autowired
    private RestTemplate restTemplate;

    // 注入自动发现客户端 DiscoveryClient
    @Autowired
    private DiscoveryClient discoveryClient;

    private URI uri;

    // ------------------ start
    /*// 定义静态变量
    private static Integer index = 0;

    // 测试 get 请求(手动负载均衡)
    @GetMapping(name = "测试 get 请求")
    public String get() {
        // ------------手动负载均衡 start
        // 获取服务的实例列表
        List<ServiceInstance> instanceList = discoveryClient.getInstances("hello_service_provider");
        // 获取到具体的实例
        // 获取实例的数量
        int size = instanceList.size();
        // 手动实现负载均衡
        // 第一次进来 index=0 ,instanceList.get(index) 返回第一个服务实例
        // 第二次进来 index=1 ,instanceList.get(index) 返回第二个服务实例
        // 第三次进来 index=2 ,将 index 对服务实例总数做取模操作index = 2mod2= 0,instanceList.get(index) 返回第一个服务实例
        ServiceInstance instance = instanceList.get(index);
        index++;
        index %= size;
        // ------------手动负载均衡 end

        // 从实例中获取url
        // uri = getUri(); // 本类抽取方法
        uri = instance.getUri();
        System.out.println(this.uri);

        // 参数一url:服务提供者的地值, responseType:服务提供者返回的相应体内容的类型
        String result = restTemplate.getForObject(this.uri + "hello", String.class);
        return result;
    }*/
    // ------------------ end

    // 测试 get 请求(Ribbon 自动负载均衡)
    @GetMapping(name = "测试 get 请求")
    public String get() {
        // 调用 hello_service_provider 提供的接口,返回数据
        // 使用服务id替换真实地址
        String url = "http://hello-service-provider";
        // 第一个参数是接口地址,第二个参数是返回值类型
        String result = restTemplate.getForObject(url + "/hello", String.class);
        return result;
    }

    // 测试 post 表单提交
    @GetMapping(value = "/postParam", name = "测试 post 表单提交功能")
    public String postParam() {
        // 从实例中获取url
        uri = getUri(); // 本类抽取方法
        System.out.println(uri);

        // 可以通过 MultiValueMap 传递表单参数,但它是个接口,用它的实现类多态接收
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        // 添加参数
        map.add("name", "tom");
        map.add("age", "18");
        String result = restTemplate.postForObject(uri + "hello/postParam", map, String.class);
        return result;
    }

    // 测试 post 请求体提交
    @GetMapping(value = "/postBody", name = "测试 post 请求体提交功能")
    public String postBody() {
        // 从实例中获取url
        uri = getUri(); // 本类抽取方法
        System.out.println(uri);

        // 创建请求的实体对象,将请求体参数封装到对象的请求体中
        User user = new User("jerry", 19);
        HttpEntity<User> httpEntity = new HttpEntity<>(user);
        String result = restTemplate.postForObject(uri + "hello/postBody", httpEntity, String.class);
        return result;
    }

    public URI getUri() {
        // 获取服务的实例列表
        List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances("hello_service_provider");
        // 获取到具体的实例
        ServiceInstance serviceInstance = serviceInstanceList.get(0);
        // 从实例中获取uri
        return serviceInstance.getUri();
    }

}
