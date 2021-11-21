package com.yuki.experiment.framework.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @TableName problem
 */
@TableName(value ="problem")
@Data
public class Problem implements Serializable {
    /**
     * 对抗练习题目ID
     */
    @ApiModelProperty("对抗练习题目ID")
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 对抗练习ID
     */
    @ApiModelProperty("对抗练习ID")
    @TableId
    private Integer counterPracticeId;

    /**
     * 课程ID
     */
    @ApiModelProperty("课程ID")
    @TableId
    private Integer courseId;

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