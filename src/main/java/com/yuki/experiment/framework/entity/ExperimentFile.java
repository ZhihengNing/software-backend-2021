package com.yuki.experiment.framework.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @TableName experiment_file
 */
@TableName(value ="experiment_file")
@Data
public class ExperimentFile implements Serializable {
    /**
     * 项目资料ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("项目资料ID")
    private Integer id;

    /**
     * 项目ID
     */
    @TableId
    @ApiModelProperty("项目ID")
    private Integer experimentId;

    /**
     * 项目资料名称
     */
    @ApiModelProperty("项目资料名称")
    private String name;

    /**
     * 教师ID
     */
    @ApiModelProperty("教师ID")
    private Integer teacherId;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 文件链接
     */
    @ApiModelProperty("文件url")
    private String url;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}