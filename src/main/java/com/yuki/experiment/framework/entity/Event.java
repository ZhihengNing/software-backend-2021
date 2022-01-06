package com.yuki.experiment.framework.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @TableName event
 */
@TableName(value ="event")
@Data
public class Event implements Serializable {
    /**
     * 自增主键
     */
    @ApiModelProperty("自增主键")
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 学生Id
     */
    @ApiModelProperty("学生Id")
    private Integer studentId;

    /**
     * 日历内容
     */
    @ApiModelProperty("日历内容")
    private String title;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty("起始时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    private Date endTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}