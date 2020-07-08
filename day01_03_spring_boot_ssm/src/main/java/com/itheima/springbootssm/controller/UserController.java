package com.itheima.springbootssm.controller;

import com.itheima.springbootssm.entity.User;
import com.itheima.springbootssm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController // @RestController = @Controller + @ResponseBody
@RequestMapping(value = "/user", name = "用户模块")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping(value = "/{id}", name = "根据id获取用户")
    public User getById(@PathVariable Integer id) {
        // 调用userService的接口
        User user = userService.getById(id);
        return user;
    }

    // 用户列表 演示使用,在企业中一般不会将所有数据一次性查询出来
    @GetMapping(name = "获取所有用户")
    public List<User> list() {
        // 调用userService的接口
        List<User> list = userService.list();
        return list;
    }

    @PostMapping(name = "新增用户")
    private Integer save(@RequestBody User user) {
        user.setUpdateTime(new Date());
        // 新增
        userService.save(user);
        // 返回保存的用户的数据库表id
        return user.getId();
    }

    @PutMapping(value = "/{id}", name = "修改用户")
    public boolean updateById(@PathVariable Integer id, @RequestBody User user) {
        user.setUpdateTime(new Date());
        user.setId(id);
        boolean update = userService.updateById(user);
        return update;
    }

    @DeleteMapping(value = "/{id}", name = "删除用户")
    public boolean deleteById(@PathVariable Integer id) {
        boolean remove = userService.removeById(id);
        return remove;
    }
}
