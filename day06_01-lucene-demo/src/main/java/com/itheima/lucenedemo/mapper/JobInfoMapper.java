package com.itheima.lucenedemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.lucenedemo.entity.JobInfo;

// BaseMapper 接口默认提供了一系列的增删改查的基础方法,JobInfoMapper接口继承之后也有了这些方法
public interface JobInfoMapper extends BaseMapper<JobInfo> {

}
