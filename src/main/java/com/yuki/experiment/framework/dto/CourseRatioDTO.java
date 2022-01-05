package com.yuki.experiment.framework.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseRatioDTO {

    @ApiModelProperty("考勤占比")
    private String attendanceRatio;

    @ApiModelProperty("实验占比")
    private String experimentRatio;

    @ApiModelProperty("对抗练习占比")
    private String practiceRatio;
}
