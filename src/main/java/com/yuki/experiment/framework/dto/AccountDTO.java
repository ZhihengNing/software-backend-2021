package com.yuki.experiment.framework.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AccountDTO {

    @ApiModelProperty("性别")
    private String gender;
    @ApiModelProperty("用户类型")
    private String type;
    @ApiModelProperty("名字")
    private String name;
}
