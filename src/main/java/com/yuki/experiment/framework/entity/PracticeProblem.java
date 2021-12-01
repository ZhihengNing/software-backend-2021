package com.yuki.experiment.framework.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName practice_problem
 */
@TableName(value ="practice_problem")
@Data
public class PracticeProblem implements Serializable {
    /**
     * 自增主键
     */
    @ApiModelProperty("自增主键")
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 对抗练习题目ID
     */
    @ApiModelProperty("对抗练习题目ID")
    private Integer problemId;

    /**
     * 对抗练习ID
     */
    @ApiModelProperty("对抗练习自增ID")
    private Integer counterPracticeAutoId;

    /**
     * 题干
     */
    @ApiModelProperty("题干")
    private String stem;

    /**
     * 参考答案
     */
    @ApiModelProperty("参考答案")
    private String referenceAnswer;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}