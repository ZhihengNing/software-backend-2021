package com.yuki.experiment.framework.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yuki.experiment.framework.dto.StudentGradeDTO;
import com.yuki.experiment.framework.entity.*;
import com.yuki.experiment.framework.mapper.mysql.*;
import com.yuki.experiment.framework.service.CourseScoreService;
import com.yuki.experiment.framework.util.GradeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
public class CourseScoreServiceImpl implements CourseScoreService {

    private final Integer EVERY_ATTENDANCE_TIMES=1;

    private final CourseScoreMapper courseScoreMapper;

    private final CourseMapper courseMapper;

    private final TeacherCourseMapper teacherCourseMapper;

    private final GradeUtil gradeUtil;

    public CourseScoreServiceImpl(CourseScoreMapper courseScoreMapper, CourseMapper courseMapper, TeacherCourseMapper teacherCourseMapper, GradeUtil gradeUtil) {
        this.courseScoreMapper = courseScoreMapper;
        this.courseMapper = courseMapper;
        this.teacherCourseMapper = teacherCourseMapper;
        this.gradeUtil = gradeUtil;
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
        courseScore.setAttendanceTimes(1);
        courseScore.setPracticeScore(BigDecimal.ZERO);
        courseScore.setCourseScore(BigDecimal.ZERO);
        updateWrapper.eq(studentId != null, "student_id", studentId)
                .eq(courseId != null, "course_id", courseId);
        return courseScoreMapper.update(courseScore, updateWrapper);
    }

    private boolean judgeTime(Date thisTime, Date lastTime){
        Calendar instance = Calendar.getInstance();
        instance.setTime(lastTime);
        Calendar instance1=Calendar.getInstance();
        instance1.setTime(thisTime);
        return instance1.get(Calendar.DAY_OF_YEAR)-instance.get(Calendar.DAY_OF_YEAR)>1;
    }

    @Override
    public CourseScore signIn(Integer studentId, Integer courseId) {
        CourseScore courseScore = getMessage(studentId, courseId);
        Date now=new Date();
        if (judge(studentId, courseId,now)) {
            Integer newTimes = courseScore.getAttendanceTimes() + EVERY_ATTENDANCE_TIMES;
            courseScore.setAttendanceTimes(newTimes);
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



    @Override
    public StudentGradeDTO getStudentGrade(Integer studentId, Integer courseId) {
//        QueryWrapper<CourseScore> wrapper = new QueryWrapper<>();
//        wrapper.eq("student_id", studentId).eq("course_id", courseId);
//        CourseScore StudentGrade = courseScoreMapper.selectOne(wrapper);
//        //得到某门课的考勤得分
//        BigDecimal attendanceScore = StudentGrade.getAttendanceScore() == null ?
//                new BigDecimal(0) : StudentGrade.getAttendanceScore();
//TODO

        return gradeUtil.getGrade(studentId, courseId);

    }

    @Override
    public CourseScore uploadPracticeGrade(Integer studentId, Integer courseId, BigDecimal grade) {
        CourseScore courseScore = getMessage(studentId, courseId);
        Date now=new Date();
        if (judge(studentId,courseId,now)) {
            BigDecimal add = courseScore.getPracticeScore().add(grade);
            courseScore.setPracticeScore(add);
            courseScore.setLastPracticeTime(now);
            UpdateWrapper<CourseScore> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("student_id", studentId).eq("course_id", courseId);
            if (courseScoreMapper.update(courseScore, updateWrapper) > 0) {
                log.info("对抗练习上传分数成功");
                return courseScore;
            }
        }
        return null;
    }

    public CourseScore getMessage(Integer studentId,Integer courseId){
        QueryWrapper<CourseScore> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", studentId)
                .eq("course_id", courseId);
        CourseScore courseScore = courseScoreMapper.selectOne(queryWrapper);
        if (courseScore == null || courseScore.getIsActive() == 0) {
            return null;
        }
        return courseScore;
    }

    public boolean judge(Integer studentId, Integer courseId,Date now){
        CourseScore courseScore = getMessage(studentId, courseId);
        Date lastAttendanceTime = courseScore.getLastAttendanceTime();
        return judgeTime(now,lastAttendanceTime);
    }
    @Override
    public boolean judgeSignIn(Integer studentId, Integer courseId) {
        return judge(studentId, courseId,new Date());
    }

}
