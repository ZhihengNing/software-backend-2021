package com.yuki.experiment.framework.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yuki.experiment.framework.entity.Course;
import com.yuki.experiment.framework.entity.CourseScore;
import com.yuki.experiment.framework.mapper.CourseMapper;
import com.yuki.experiment.framework.mapper.CourseScoreMapper;
import com.yuki.experiment.framework.service.CourseScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseScoreServiceImpl implements CourseScoreService {

    @Autowired
    private CourseScoreMapper courseScoreMapper;

    @Autowired
    private CourseMapper courseMapper;
    @Override
    public List<JSONObject> getCourseInfoAndIsActive(Integer studentID) {
        QueryWrapper<CourseScore>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("student_id",studentID);
        List<CourseScore> courseScores = courseScoreMapper.selectList(queryWrapper);
        List<JSONObject> list=new ArrayList<>();
        for (CourseScore item : courseScores) {
            Integer isActive = item.getIsActive();
            JSONObject json = new JSONObject();
            Integer courseId = item.getCourseId();
            Course course = courseMapper.selectOne(new QueryWrapper<Course>().eq("id", courseId));
            json.put("isActive", isActive);
            json.put("courseInfo", course);
            list.add(json);
        }
        return list;
    }

    @Override
    public int setCourseActive(Integer studentId, Integer courseId) {
        UpdateWrapper<CourseScore> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("is_active", 1)
                .eq(studentId!=null,"student_id", studentId)
                .eq(courseId!=null,"course_id", courseId);
        return courseScoreMapper.update(new CourseScore(),updateWrapper);
    }
}
