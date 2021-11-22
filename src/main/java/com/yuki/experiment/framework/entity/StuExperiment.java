package com.yuki.experiment.framework.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

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
    @ApiModelProperty("学生ID")
    private Integer studentId;

    /**
     * 实验项目ID
     */
    @TableId
    @ApiModelProperty("实验项目ID")
    private Integer experimentId;

    /**
     * 项目分数
     */
    @ApiModelProperty("项目分数")
    private BigDecimal experimentScore;

    /**
     * 文件id
     */
    @ApiModelProperty("文件ID")
    private Integer fileId;

    /**
     * 作业内容
     */
    @ApiModelProperty("作业内容")
    private String jobContent;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}