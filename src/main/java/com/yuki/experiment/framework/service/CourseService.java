package com.yuki.experiment.framework.service;


import com.yuki.experiment.framework.dto.CourseRatioDTO;
import com.yuki.experiment.framework.dto.StudentGradeDTO;
import com.yuki.experiment.framework.entity.Course;

import java.math.BigDecimal;
import java.util.List;

public interface CourseService {

    int insert(Course course);

    List<Course> getCourseInfoByTeacher(Integer teacherId,Integer courseId);

    Course getCourseInfo(Integer studentId,Integer courseId);

    CourseRatioDTO setRatio(Integer courseId,Integer teacherId,
            String attendanceRatio,String experimentRatio, String practiceRatio);

    List<StudentGradeDTO> getTakeStudent(Integer courseId);

}
