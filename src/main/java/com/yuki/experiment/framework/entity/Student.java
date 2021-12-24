package com.yuki.experiment.framework.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @TableName student
 */
@TableName(value ="student")
@Data
public class Student implements Serializable {
    /**
     * 学生ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("学生Id")
    private Integer id;

    /**
     * 管理员ID
     */
    @ApiModelProperty("管理员Id")
    private Integer administratorId;

    /**
     * 学生姓名
     */
    @ApiModelProperty("学生姓名")
    private String name;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 性别
     */
    @ApiModelProperty("性别")
    private String gender;

    /**
     * 年级
     */
    @ApiModelProperty("年级")
    private String year;

    /**
     * 电话号码
     */
    @ApiModelProperty("电话号码")
    private String phoneNum;

    /**
     * 总成绩
     */
    @ApiModelProperty("总成绩")
    private BigDecimal grade;

    /**
     * 账户创建时间
     */
    @ApiModelProperty("账户创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 账户修改时间
     */
    @ApiModelProperty("账户修改时间")
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 专业
     */
    @ApiModelProperty("专业")
    private String major;

    /**
     * 邮箱账号
     */
    @ApiModelProperty("邮箱账号")
    private String mailbox;

    /**
     * 头像url
     */
    @ApiModelProperty("头像url")
    private String url;

    /**
     * 地址
     */
    @ApiModelProperty("地址")
    private String address;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}