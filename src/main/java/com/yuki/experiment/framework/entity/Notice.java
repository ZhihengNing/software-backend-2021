package com.yuki.experiment.framework.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @TableName notice
 */
@TableName(value ="notice")
@Data
public class Notice implements Serializable {
    /**
     * 公告ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("公告ID")
    private Integer id;

    /**
     * 公告创建时间
     */
    @ApiModelProperty("公告创建时间")
    @TableField(fill= FieldFill.INSERT)
    private Date createTime;

    /**
     * 公告修改时间
     */
    @ApiModelProperty("公告修改时间")
    @TableField(fill=FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 公告标题
     */
    @ApiModelProperty("公告标题")
    private String noticeTitle;

    /**
     * 公告内容
     */
    @ApiModelProperty("公告内容")
    private String noticeText;

    /**
     * 公告发布人ID（即管理员ID)
     */
    @ApiModelProperty("公告发布人ID")
    private Integer administratorId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}