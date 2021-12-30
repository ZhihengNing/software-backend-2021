package com.yuki.experiment.framework.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @TableName stu_experiment
 */
@TableName(value ="stu_experiment")
@Data
@Builder
public class StuExperiment implements Serializable {
    /**
     * 学生ID
     */
    @TableId
    @ApiModelProperty("学生Id")
    private Integer studentId;

    /**
     * 实验项目ID
     */
    @TableId
    @ApiModelProperty("实验项目Id")
    private Integer experimentId;

    /**
     * 项目分数
     */
    @ApiModelProperty("项目分数")
    private BigDecimal experimentScore;

    /**
     * 文件url
     */
    @ApiModelProperty("文件Url")
    private String url;

    /**
     * 作业内容
     */
    @ApiModelProperty("作业内容")
    private String jobContent;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}