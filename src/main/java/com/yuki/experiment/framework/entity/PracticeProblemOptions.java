package com.yuki.experiment.framework.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName practice_problem_options
 */
@TableName(value ="practice_problem_options")
@Data
public class PracticeProblemOptions implements Serializable {
    /**
     * 主码
     */
    @ApiModelProperty("自增主键")
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 选项ID
     */
    @ApiModelProperty("选项Id")
    private Integer topicOptionsId;

    /**
     * 对抗练习题目ID
     */
    @ApiModelProperty("对抗练习题目自增Id")
    private Integer problemAutoId;

    /**
     * 题目内容
     */
    @ApiModelProperty("题目内容")
    private String content;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}