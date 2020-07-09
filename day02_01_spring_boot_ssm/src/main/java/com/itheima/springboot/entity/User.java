package com.itheima.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @TableId
    private Integer id;
    private String username;
    private String password;
    private Integer age;

    private Date updateTime;
}
