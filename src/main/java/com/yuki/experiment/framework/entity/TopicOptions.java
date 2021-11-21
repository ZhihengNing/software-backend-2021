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
 * @TableName topic_options
 */
@TableName(value ="topic_options")
@Data
public class TopicOptions implements Serializable {
    /**
     * 选项ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("选项ID")
    private Integer id;

    /**
     * 题目ID
     */
    @TableId
    @ApiModelProperty("题目ID")
    private Integer problemId;

    /**
     * 对抗练习ID
     */
    @TableId
    @ApiModelProperty("对抗练习ID")
    private Integer counterPracticeId;

    /**
     * 课程ID
     */
    @TableId
    @ApiModelProperty("课程ID")
    private Integer courseId;

    /**
     * 题目内容
     */
    @ApiModelProperty("选项内容")
    private String content;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}