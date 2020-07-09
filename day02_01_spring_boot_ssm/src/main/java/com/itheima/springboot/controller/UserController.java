package com.itheima.springboot.controller;

import com.itheima.springboot.entity.User;
import com.itheima.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/user", name = "用户模块")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping(value = "/{id}", name = "根据id查询用户")
    public String getUserById(@PathVariable Integer id, HttpServletRequest request) {
        // 获取用户
        User user = userService.getById(id);
        // 将用户数据存到request域中
        // model.addAttribute("user", user); // Model 也可以
        request.setAttribute("user", user);

        // 前缀prefix: classpath:/templates/  后缀suffix: .html
        return "user";
    }

}
