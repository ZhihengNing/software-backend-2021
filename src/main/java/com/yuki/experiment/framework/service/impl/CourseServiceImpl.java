package com.yuki.experiment.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuki.experiment.framework.entity.Course;
import com.yuki.experiment.framework.entity.CourseScore;
import com.yuki.experiment.framework.mapper.CourseMapper;
import com.yuki.experiment.framework.mapper.CourseScoreMapper;
import com.yuki.experiment.framework.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    private CourseMapper courseMapper;

    private CourseScoreMapper courseScoreMapper;

    @Autowired
    public void setCourseMapper(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }
    @Autowired
    public void setCourseScoreMapper(CourseScoreMapper courseScoreMapper) {
        this.courseScoreMapper = courseScoreMapper;
    }

    @Override
    public int insert(Course course) {
        return courseMapper.insert(course);
    }

    @Override
    public Course getCourseInfoByID(Integer studentId,Integer courseId) {
        QueryWrapper<Course>queryWrapper=new QueryWrapper<>();
        Course id = courseMapper.selectOne(queryWrapper.eq("id", courseId));
        QueryWrapper<CourseScore>queryWrapper1=new QueryWrapper<>();
        queryWrapper1.eq("course_id",id.getId()).eq("student_id",studentId);
        if(courseScoreMapper.selectOne(queryWrapper1).getIsActive()==1){
            return id;
        }
        return null;
    }

    @Override
    public BigDecimal setCourseAttendanceRatio(Integer courseId, BigDecimal attendanceRatio) {
        Course course=new Course();
        course.setId(courseId);
        course.setAttendanceRatio(attendanceRatio);
        if(courseMapper.updateById(course)>0){
            return course.getAttendanceRatio();
        }
        return null;
    }

}
