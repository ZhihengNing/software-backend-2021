package com.yuki.experiment.framework.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @TableName course
 */
@TableName(value ="course")
@Data
public class Course implements Serializable {
    /**
     * 课程ID
     */
    @ApiModelProperty("课程Id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 课程名称
     */
    @ApiModelProperty("课程名称")
    private String name;

    /**
     * 授课地点
     */
    @ApiModelProperty("授课地点")
    private String place;

    /**
     * 课程开课时间段
     */
    @ApiModelProperty("课程开课时间段")
    private String openPeriod;

    /**
     * 课程信息创建时间
     */
    @ApiModelProperty("课程信息创建时间")
    private Date createTime;

    /**
     * 课程信息修改时间
     */
    @ApiModelProperty("课程信息修改时间")
    private Date updateTime;

    /**
     * 课程创建人ID(即责任教师）
     */
    @ApiModelProperty("课程创建人Id")
    private Integer teacherId;

    /**
     * 考勤得分比例
     */
    @ApiModelProperty("各种得分比例，string数组")
    private String scoreRatio;

    /**
     * 课程学分
     */
    @ApiModelProperty("课程学分")
    private Integer credit;

    /**
     * 课程所属学院
     */
    @ApiModelProperty("课程所属学院")
    private String college;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}