package com.yuki.experiment.framework.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @TableName student_upload_file
 */
@TableName(value ="student_upload_file")
@Data
public class StudentUploadFile implements Serializable {
    /**
     * 学生上传文件ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("学生上传文件ID")
    private Integer id;

    /**
     * 文件名字
     */
    @ApiModelProperty("文件名字")
    private String name;

    /**
     * 文件上传时间
     */
    @ApiModelProperty("文件上传时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 文件修改时间
     */
    @ApiModelProperty("文件修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 文件修改时间
     */
    @ApiModelProperty("文件url")
    private String url;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}