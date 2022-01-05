package com.yuki.experiment.framework.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Practice implements Serializable {

    @ApiModelProperty("主键唯一标识")
    private String id;

    @ApiModelProperty("课程Id")
    private Integer courseId;

    @ApiModelProperty("教师Id")
    private Integer teacherId;

    @ApiModelProperty("课程题目")
    private List<Problem> problems;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;



}
