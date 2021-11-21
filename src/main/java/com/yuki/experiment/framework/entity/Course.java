package com.yuki.experiment.framework.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
    @ApiModelProperty("课程ID")
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
    @TableField(fill= FieldFill.INSERT)
    private Date createTime;

    /**
     * 课程信息修改时间
     */
    @ApiModelProperty("课程信息修改时间")
    @TableField(fill=FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 课程创建人ID
     */
    @ApiModelProperty("课程创建人ID(即责任教师)")
    private Integer teacherId;

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