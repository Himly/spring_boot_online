package com.itheima.vuedemo.service;

import com.itheima.vuedemo.mapper.UserMapper;
import com.itheima.vuedemo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> findAll() {
        return userMapper.selectUserList();
    }

    public User findById(Long userId) {
        return userMapper.selectUserById(userId);
    }

    public void updateUser(User user) {
        userMapper.updateUser(user);
    }
}
