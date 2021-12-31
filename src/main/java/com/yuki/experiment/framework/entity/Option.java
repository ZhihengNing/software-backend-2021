package com.yuki.experiment.framework.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Option implements Serializable {

    @ApiModelProperty("选项的key")
    private String key;

    @ApiModelProperty("选项答案")
    private String value;

    @ApiModelProperty("参考答案")
    private String reference;
}
