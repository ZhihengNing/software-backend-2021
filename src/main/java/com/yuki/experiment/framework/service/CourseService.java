package com.yuki.experiment.framework.service;


import com.yuki.experiment.framework.entity.Course;

import java.math.BigDecimal;
import java.util.List;

public interface CourseService {

    int insert(Course course);

    Course getCourseInfoByID(Integer studentId,Integer courseId);

    List<BigDecimal> getCourseGrade(Integer studentId,Integer courseId);
}
