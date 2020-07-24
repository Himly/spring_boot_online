package com.itheima.vuedemo.mapper;

import com.itheima.vuedemo.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/*
    操作 user_info表
 */
// @Mapper // 将这个Mapper交给 Spring ioc 容器来管理,然后就可以在其他地方直接中注入这个实例了
// 但是如果我有多个XxxMapper,一个个打注解太麻烦了,所以我们可以在启动类上配置Mapper包扫描,这样就可以在启动类启动的时候将mapper包下的所有Xxxmapper扫描并放到ioc容器中
public interface UserMapper {

    @Select("select id,age,user_name as userName,pass_word as passWord,email,sex from user")
    List<User> selectUserList();

    @Select("select id,age,user_name as userName,pass_word as passWord,email,sex from user where id = #{userId}")
    User selectUserById(Long userId);

    @Select("update user set age = #{age},user_name = #{userName},email = #{email} where id = #{id}")
    void updateUser(User user);


}
