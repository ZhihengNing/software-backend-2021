package com.yuki.experiment.framework.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Problem {

    @ApiModelProperty("题干")
    private String stem;

    @ApiModelProperty("具体选项")
    private List<Option> options;

    @ApiModelProperty("练习分值")
    private BigDecimal score;


}
