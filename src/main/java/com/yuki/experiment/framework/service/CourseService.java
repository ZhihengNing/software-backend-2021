package com.yuki.experiment.framework.service;


import com.yuki.experiment.framework.entity.Course;

public interface CourseService {

    int insert(Course course);

    Course getCourseInfoByID(Integer courseId);
}
