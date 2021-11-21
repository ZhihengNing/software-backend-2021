package com.yuki.experiment.framework.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @TableName course_notice
 */
@TableName(value ="course_notice")
@Data
public class CourseNotice implements Serializable {
    /**
     * 课程公告ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("课程公告ID")
    private Integer id;

    /**
     * 课程ID
     */
    @TableId
    @ApiModelProperty("课程ID")
    private Integer courseId;

    /**
     * 公告标题
     */
    @ApiModelProperty("公告标题")
    private String title;

    /**
     * 公告内容
     */
    @ApiModelProperty("公告内容")
    private String content;

    /**
     * 公告发布时间
     */
    @ApiModelProperty("公告发布时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 公告修改时间
     */
    @ApiModelProperty("公告修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 创建人ID
     */
    @ApiModelProperty("创建人ID")
    private Integer teacherId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}