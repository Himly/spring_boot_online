package com.itheima.lucenedemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.lucenedemo.entity.JobInfo;
import com.itheima.lucenedemo.mapper.JobInfoMapper;
import com.itheima.lucenedemo.service.IJobInfoService;
import org.springframework.stereotype.Service;

@Service
public class JobInfoServiceImpl extends ServiceImpl<JobInfoMapper, JobInfo> implements IJobInfoService {

}
