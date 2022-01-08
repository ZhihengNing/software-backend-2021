package com.yuki.experiment.framework.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @TableName course_score
 */
@TableName(value ="course_score")
@Data
public class CourseScore implements Serializable {
    /**
     * 课程ID
     */
    @ApiModelProperty("课程Id")
    @TableId
    private Integer courseId;

    /**
     * 学生ID
     */
    @ApiModelProperty("学生Id")
    @TableId
    private Integer studentId;

    /**
     * 课程得分
     */
    @ApiModelProperty("课程得分")
    private BigDecimal courseScore;

    /**
     * 上次考勤时间
     */
    @ApiModelProperty("上次考勤时间")
    private Date lastAttendanceTime;

    @ApiModelProperty("上次对抗练习时间")
    private Date lastPracticeTime;
    /**
     * 考勤次数
     */
    @ApiModelProperty("考勤次数")
    private Integer attendanceTimes;

    /**
     * 是否激活
     */
    @ApiModelProperty("是否激活")
    private Integer isActive;

    /**
     * 对抗练习分数
     */
    @ApiModelProperty("对抗练习分数")
    private BigDecimal practiceScore;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}