package com.itheima.springbootssm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.springbootssm.entity.User;
import com.itheima.springbootssm.mapper.UserMapper;
import com.itheima.springbootssm.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
