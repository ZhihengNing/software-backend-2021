package com.yuki.experiment.framework.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @TableName teacher
 */
@TableName(value ="teacher")
@Data
public class Teacher implements Serializable {
    /**
     * 教师用户id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("教师用户ID")
    private Integer id;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 名字
     */
    @ApiModelProperty("名字")
    private String name;

    /**
     * 性别
     */
    @ApiModelProperty("性别")
    private String gender;

    /**
     * 账户创建时间
     */
    @ApiModelProperty("账户创建时间")
    @TableField(fill=FieldFill.INSERT)
    private Date createTime;

    /**
     * 账户更新时间
     */
    @ApiModelProperty("账户更新时间")
    @TableField(fill= FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 邮箱账号
     */
    @ApiModelProperty("邮箱账号")
    private String mailbox;

    /**
     * 管理员id
     */
    @ApiModelProperty("管理员ID")
    private Integer administratorId;

    /**
     * 教师头像url
     */
    @ApiModelProperty("教师头像URL")
    private String url;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}