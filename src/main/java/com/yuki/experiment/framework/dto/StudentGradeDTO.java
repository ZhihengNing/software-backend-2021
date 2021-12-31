package com.yuki.experiment.framework.dto;

import com.yuki.experiment.framework.entity.Practice;
import com.yuki.experiment.framework.entity.CourseScore;

import com.yuki.experiment.framework.entity.StuExperiment;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StudentGradeDTO {
    private CourseScore courseScore;

    private List<StuExperiment>experimentScore;

    private List<Practice>practiceScore;

}
