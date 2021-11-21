package com.yuki.experiment.framework.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @TableName course_feedback
 */
@TableName(value ="course_feedback")
@Data
public class CourseFeedback implements Serializable {
    /**
     * 课程反馈ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("课程反馈ID")
    private Integer id;

    /**
     * 学生ID
     */
    @TableId
    @ApiModelProperty("学生ID")
    private Integer studentId;

    /**
     * 课程ID
     */
    @TableId
    @ApiModelProperty("课程ID")
    private Integer courseId;

    /**
     * 标题
     */
    @ApiModelProperty("标题")
    private String title;

    /**
     * 内容
     */
    @ApiModelProperty("内容")
    private String content;

    /**
     * 创建时间
     */
    @ApiModelProperty("课程反馈创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty("课程反馈修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}