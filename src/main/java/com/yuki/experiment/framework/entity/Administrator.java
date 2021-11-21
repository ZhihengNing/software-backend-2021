package com.yuki.experiment.framework.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @TableName administrator
 */
@TableName(value ="administrator")
@Data
public class Administrator implements Serializable {
    /**
     * 管理员ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("管理员ID")
    private Integer id;

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
     * 管理员姓名
     */
    @ApiModelProperty("管理员姓名")
    private String name;

    /**
     * 账户创建时间
     */
    @ApiModelProperty("账户创建时间")
    @TableField(fill=FieldFill.INSERT)
    private Date createTime;

    /**
     * 账户修改时间
     */
    @ApiModelProperty("账户修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 管理员邮箱
     */
    @ApiModelProperty("管理员邮箱")
    private String mailbox;

    /**
     * 管理员头像url
     */
    @ApiModelProperty("管理员头像url")
    private String url;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}