package com.itheima.vuedemo.controller;

import com.itheima.vuedemo.pojo.User;
import com.itheima.vuedemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user", name = "用户模块")
public class UserInfoController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/findAll", name = "显示用户列表")
    public List<User> findAllUser() {
        return userService.findAll();
    }

    @GetMapping(value = "/findById/{id}", name = "根据id查询用户")
    public User findAllUser(@PathVariable(name = "id") Long userId) { // 如果id 和 Long userId 不一致,则@PathVariable(name = "id"); 一致则 @PathVariable
        return userService.findById(userId);
    }

    @PostMapping(value = "/update", name = "修改用户信息")
    public void findAllUser(@RequestBody User user) { // 如果id 和 Long userId 不一致,则@PathVariable(name = "id"); 一致则 @PathVariable
        userService.updateUser(user);
    }
}
