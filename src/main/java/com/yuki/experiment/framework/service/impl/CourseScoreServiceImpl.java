package com.yuki.experiment.framework.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yuki.experiment.framework.dto.SignInDTO;
import com.yuki.experiment.framework.entity.Course;
import com.yuki.experiment.framework.entity.CourseScore;
import com.yuki.experiment.framework.mapper.CourseMapper;
import com.yuki.experiment.framework.mapper.CourseScoreMapper;
import com.yuki.experiment.framework.mapper.TeacherCourseMapper;
import com.yuki.experiment.framework.service.CourseScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CourseScoreServiceImpl implements CourseScoreService {

    private final BigDecimal EVERY_ATTENDANCE_SCORE=BigDecimal.ONE;

    private final CourseScoreMapper courseScoreMapper;

    private final CourseMapper courseMapper;

    private final TeacherCourseMapper teacherCourseMapper;

    public CourseScoreServiceImpl(CourseScoreMapper courseScoreMapper,
                                  CourseMapper courseMapper,
                                  TeacherCourseMapper teacherCourseMapper) {
        this.courseScoreMapper = courseScoreMapper;
        this.courseMapper = courseMapper;
        this.teacherCourseMapper = teacherCourseMapper;
    }

    @Override
    public List<JSONObject> getCourseInfoAndIsActive(Integer studentID) {
        QueryWrapper<CourseScore>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("student_id",studentID);
        List<CourseScore> courseScores = courseScoreMapper.selectList(queryWrapper);
        List<JSONObject> list=new ArrayList<>();
        System.out.println(courseScores.size());
        for (CourseScore item : courseScores) {
            Integer isActive = item.getIsActive();
            JSONObject json = new JSONObject();
            Integer courseId = item.getCourseId();
            json.put("courseId",courseId);
            JSONObject courseInfo = courseMapper.getCourseInfo(courseId);
            List<JSONObject> teacherInfo = teacherCourseMapper.getInfo(courseId);
            courseInfo.put("teacherInfo",teacherInfo);
            json.put("isActive", isActive);
            json.put("courseInfo", courseInfo);
            list.add(json);
        }
        return list;
    }

    @Override
    public int setCourseActive(Integer studentId, Integer courseId) {
        QueryWrapper<CourseScore> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", studentId)
                .eq("course_id", courseId);
        CourseScore courseScore = courseScoreMapper.selectOne(queryWrapper);
        if (courseScore==null||courseScore.getIsActive() != 0) {
            return 0;
        }
        UpdateWrapper<CourseScore> updateWrapper = new UpdateWrapper<>();
        Date now = new Date();
        courseScore.setIsActive(1);
        courseScore.setLastAttendanceTime(now);
        courseScore.setAttendanceScore(BigDecimal.ONE);
        updateWrapper.eq(studentId != null, "student_id", studentId)
                .eq(courseId != null, "course_id", courseId);
        return courseScoreMapper.update(courseScore, updateWrapper);
    }

    private boolean judgeSignIn(Date thisTime,Date lastTime){
        Calendar instance = Calendar.getInstance();
        instance.setTime(lastTime);
        Calendar instance1=Calendar.getInstance();
        instance1.setTime(thisTime);
        return instance1.get(Calendar.DAY_OF_YEAR)-instance.get(Calendar.DAY_OF_YEAR)>1;
    }

    @Override
    public CourseScore signIn(Integer studentId, Integer courseId) {
        QueryWrapper<CourseScore> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", studentId)
                .eq("course_id", courseId);
        CourseScore courseScore = courseScoreMapper.selectOne(queryWrapper);
        if (courseScore==null||courseScore.getIsActive() == 0) {
            return null;
        }
        Date lastAttendanceTime = courseScore.getLastAttendanceTime();
        Date now = new Date();
        if (judgeSignIn(now, lastAttendanceTime)) {
            BigDecimal newScore = courseScore.getAttendanceScore().add(EVERY_ATTENDANCE_SCORE);
            courseScore.setAttendanceScore(newScore);
            courseScore.setLastAttendanceTime(now);
            UpdateWrapper<CourseScore> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("student_id", studentId).eq("course_id", courseId);
            if (courseScoreMapper.update(courseScore, updateWrapper) > 0) {
                log.info("考勤成功");
                return courseScore;
            }
        }
        return null;
    }


}
