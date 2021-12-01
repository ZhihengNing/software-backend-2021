package com.yuki.experiment.framework.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @TableName experiment
 */
@TableName(value ="experiment")
@Data
public class Experiment implements Serializable {
    /**
     * 实验项目ID
     */
    @ApiModelProperty("实验项目Id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 课程ID
     */
    @ApiModelProperty("课程Id")
    @TableId
    private Integer courseId;

    /**
     * 实验项目名称
     */
    @ApiModelProperty("实验项目名称")
    private String name;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private Date updateTime;

    /**
     * 创建人ID
     */
    @ApiModelProperty("创建人Id")
    private Integer teacherId;

    /**
     * 实验项目分值
     */
    @ApiModelProperty("实验项目分值")
    private BigDecimal experimentScore;

    /**
     * 实验项目分数比例
     */
    @ApiModelProperty("实验项目分数比例")
    private BigDecimal experimentScoreRatio;

    /**
     * 实验项目目的
     */
    @ApiModelProperty("实验项目目的")
    private String experimentPurpose;

    /**
     * 实验项目内容
     */
    @ApiModelProperty("实验项目内容")
    private String experimentContent;

    /**
     * 实验项目截至时间
     */
    @ApiModelProperty("实验项目截至时间")
    private Date experimentDeadline;

    /**
     * 实验项目备注
     */
    @ApiModelProperty("实验项目备注")
    private String experimentRemarks;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}