package com.itheima.springbootssm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
// user实体类对应的数据库表名
// @TableName("t_user") // 因为 application.yml 中设置了 table-prefix: t_(表名前缀),所以 这个可以省略
public class User {
    // 主键 IdType.AUTO 表示数据库主键自增
    // @TableId(type = IdType.AUTO)
    @TableId // 因为 application.yml 中设置了 id-type: auto(数据库ID自增),所以 @TableId(type = IdType.AUTO) 简写为 @TableId
    private Integer id;
    private String username;
    private String password;
    private Integer age;

    // 实体类属性和对应数据表的字段名不一样需要手动映射
    // @TableField("update_Time") // 因为application.yml 中设置了map-underscore-to-camel-case: true(开启自动驼峰命名规则映射),所以 这个也可以省略
    private Date updateTime;
}
