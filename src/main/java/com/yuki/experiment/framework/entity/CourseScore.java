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
    @TableId
    @ApiModelProperty("课程ID")
    private Integer courseId;

    /**
     * 学生ID
     */
    @TableId
    @ApiModelProperty("学生ID")
    private Integer studentId;

    /**
     * 课程得分
     */
    @ApiModelProperty("课程得分")
    private BigDecimal courseScore;

    /**
     * 这次考勤时间
     */
    @ApiModelProperty("此次考勤时间")
    private Date thisAttendanceTime;

    /**
     * 上次考勤时间
     */
    @ApiModelProperty("上次考勤时间")
    private Date lastAttendanceTime;

    /**
     * 考勤得分
     */
    @ApiModelProperty("考勤得分")
    private BigDecimal attendanceScore;

    /**
     * 是否激活
     */
    @ApiModelProperty("是否激活")
    private Integer isActive;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}