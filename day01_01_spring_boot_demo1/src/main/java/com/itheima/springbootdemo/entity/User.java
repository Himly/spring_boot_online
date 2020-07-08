package com.itheima.springbootdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String username;

    private String password;

    private Integer age;

    private String[] girlNames; // 字符串数组

    private List<String> boyList; // 字符串集合

    private List<User> userList; // 对象集合
}
