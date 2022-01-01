package com.yuki.experiment.framework.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseRatioDTO {

    private String attendanceRatio;

    private String experimentRatio;

    private String practiceRatio;
}
