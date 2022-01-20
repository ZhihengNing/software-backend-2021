package com.yuki.experiment.framework.dto;

import com.yuki.experiment.framework.entity.CourseScore;
import com.yuki.experiment.framework.entity.StuExperiment;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StudentGradeDTO {

    @ApiModelProperty("学生Id")
    private Integer studentId;

    @ApiModelProperty("学生姓名")
    private String studentName;

    @ApiModelProperty("学生选的某门课")
    private CourseScore take;

    @ApiModelProperty("所有的实验")
    private List<StuExperiment> experiments;

}
