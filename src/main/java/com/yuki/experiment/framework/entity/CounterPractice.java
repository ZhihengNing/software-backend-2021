package com.yuki.experiment.framework.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @TableName counter_practice
 */
@TableName(value ="counter_practice")
@Data
public class CounterPractice implements Serializable {
    /**
     * 对抗练习ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("对抗练习ID")
    private Integer id;

    /**
     * 课程ID
     */
    @ApiModelProperty("课程ID")
    @TableId
    private Integer courseId;

    /**
     * 练习创建时间
     */
    @ApiModelProperty("练习创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 练习修改时间
     */
    @ApiModelProperty("练习修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 练习开始时间
     */
    @ApiModelProperty("练习开始时间")
    private Date startTime;

    /**
     * 练习结束时间
     */
    @ApiModelProperty("练习结束时间")
    private Date endTime;

    /**
     * 创建人ID
     */
    @ApiModelProperty("创建人ID")
    private Integer teacherId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}