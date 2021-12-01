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
 * @TableName practice
 */
@TableName(value ="practice")
@Data
public class Practice implements Serializable {
    /**
     * 自增主键
     */
    @ApiModelProperty("自增主键")
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 对抗练习ID
     */
    @ApiModelProperty("对抗练习ID")
    private Integer counterPracticeId;

    /**
     * 课程ID
     */
    @ApiModelProperty("课程ID")
    private Integer courseId;

    /**
     * 练习创建时间
     */
    @ApiModelProperty("练习创建时间")
    private Date createTime;

    /**
     * 练习修改时间
     */
    @ApiModelProperty("练习修改时间")
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

    /**
     * 练习分值
     */
    @ApiModelProperty("练习分值")
    private BigDecimal score;

    /**
     * 练习所占比例
     */
    @ApiModelProperty("练习所占比例")
    private BigDecimal ratio;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}