package com.yuki.experiment.framework.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @TableName course_file
 */
@TableName(value ="course_file")
@Data
public class CourseFile implements Serializable {
    /**
     * 课程资料ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("课程资料ID")
    private Integer id;

    /**
     * 课程ID
     */
    @TableId
    @ApiModelProperty("课程ID")
    private Integer courseId;

    /**
     * 课程资料名称
     */
    @ApiModelProperty("课程资料名称")
    private String name;

    /**
     * 文件创建时间
     */
    @ApiModelProperty("文件创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 文件修改时间
     */
    @ApiModelProperty("文件修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 上传人ID
     */
    @ApiModelProperty("上传人ID")
    private Integer teacherId;

    /**
     * 文件链接
     */
    @ApiModelProperty("文件url")
    private String url;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}