package com.yuki.experiment.framework.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName role
 */
@TableName(value ="role")
@Data
public class Role implements Serializable {
    /**
     * 角色ID
     */
    @TableId
    @ApiModelProperty("角色ID")
    private Integer id;

    /**
     * 角色名字
     */
    @ApiModelProperty("角色名字")
    private String roleName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}