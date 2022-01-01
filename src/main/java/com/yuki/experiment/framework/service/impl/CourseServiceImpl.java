package com.yuki.experiment.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yuki.experiment.framework.dto.CourseRatioDTO;
import com.yuki.experiment.framework.entity.Course;
import com.yuki.experiment.framework.entity.CourseScore;
import com.yuki.experiment.framework.mapper.mysql.CourseMapper;
import com.yuki.experiment.framework.mapper.mysql.CourseScoreMapper;
import com.yuki.experiment.framework.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
    public Course getCourseInfo(Integer studentId,Integer courseId) {
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
    public CourseRatioDTO setRatio(Integer courseId,Integer teacherId,
                                   String attendanceRatio,
                                   String experimentRatio,
                                   String practiceRatio) {
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("id", courseId)
                .eq("teacher_id", teacherId);
        Course course = courseMapper.selectOne(courseQueryWrapper);
        if (course == null) {
            return null;
        }
        String scoreRatio = course.getScoreRatio();
        if (scoreRatio == null) {
            course.setScoreRatio(attendanceRatio + "," + experimentRatio + "," + practiceRatio);
            courseMapper.updateById(course);
            return CourseRatioDTO.builder().attendanceRatio(attendanceRatio)
                    .experimentRatio(experimentRatio).practiceRatio(practiceRatio).build();
        }
        String[] split = scoreRatio.split(",");
        if (attendanceRatio != null) {
            split[0] = attendanceRatio;
        }
        if (experimentRatio != null) {
            split[1] = experimentRatio;
        }
        if (practiceRatio != null) {
            split[2] = practiceRatio;
        }
        CourseRatioDTO build = CourseRatioDTO.builder().attendanceRatio(split[0])
                .experimentRatio(split[1])
                .practiceRatio(split[2]).build();
        String s = Arrays.stream(split).reduce((a, b) -> a + "," + b).get();
        course.setScoreRatio(s);
        courseMapper.updateById(course);
        return build;
    }

}
