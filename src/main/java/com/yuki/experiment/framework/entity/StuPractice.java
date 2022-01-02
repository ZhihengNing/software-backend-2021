package com.yuki.experiment.framework.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StuPractice {

    @ApiModelProperty("学生Id")
    private Integer studentId;

    @ApiModelProperty("练习")
    private Practice practice;

    @ApiModelProperty("学生分数")
    private BigDecimal studentScore;
}
