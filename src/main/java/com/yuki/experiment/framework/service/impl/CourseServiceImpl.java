package com.yuki.experiment.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuki.experiment.framework.entity.Course;
import com.yuki.experiment.framework.mapper.CourseMapper;
import com.yuki.experiment.framework.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper mapper;
    @Override
    public int insert(Course course) {
        return mapper.insert(course);
    }

    @Override
    public Course getCourseInfoByID(Integer courseId) {
        QueryWrapper<Course>queryWrapper=new QueryWrapper<>();
        return mapper.selectOne(queryWrapper.eq("id",courseId));
    }
}
