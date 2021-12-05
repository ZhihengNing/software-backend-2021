package com.yuki.experiment.framework.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * 
 * @TableName teacher_course
 */
@TableName(value ="teacher_course")
@Data
public class TeacherCourse implements Serializable {
    /**
     * 教师ID
     */
    @TableId
    @ApiModelProperty("教师Id")
    private Integer teacherId;

    /**
     * 课程ID
     */
    @TableId
    @ApiModelProperty("课程Id")
    private Integer courseId;

    /**
     * 角色名称
     */
    @TableId
    @ApiModelProperty("角色名称")
    private String characterName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}