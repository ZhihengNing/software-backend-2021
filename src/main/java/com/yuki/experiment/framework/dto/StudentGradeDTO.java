package com.yuki.experiment.framework.dto;

import com.yuki.experiment.framework.entity.CourseScore;
import com.yuki.experiment.framework.entity.StuExperiment;
import com.yuki.experiment.framework.entity.StuPractice;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StudentGradeDTO {

    private CourseScore courseScore;

    private List<StuExperiment> experimentScore;

    private List<StuPractice> practiceScore;

}
