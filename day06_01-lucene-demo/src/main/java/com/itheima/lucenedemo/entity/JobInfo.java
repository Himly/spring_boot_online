package com.itheima.lucenedemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键 id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 公司联系方式
     */
    private String companyAddr;

    /**
     * 公司信息
     */
    private String companyInfo;

    /**
     * 职位名称
     */
    private String jobName;

    /**
     * 工作地点
     */
    private String jobAddr;

    /**
     * 职位信息
     */
    private String jobInfo;

    /**
     * 薪资范围，最小
     */
    private Integer salaryMin;

    /**
     * 薪资范围，最大
     */
    private Integer salaryMax;

    /**
     * 招聘信息详情页
     */
    private String url;

    /**
     * 职位最近发布时间
     */
    private String time;
}
