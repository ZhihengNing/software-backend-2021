package com.yuki.experiment.framework.service;


import com.yuki.experiment.framework.entity.Course;

import java.math.BigDecimal;

public interface CourseService {

    int insert(Course course);

    Course getCourseInfoByID(Integer studentId,Integer courseId);

    BigDecimal setCourseAttendanceRatio(Integer courseId,BigDecimal attendanceRatio);

}
