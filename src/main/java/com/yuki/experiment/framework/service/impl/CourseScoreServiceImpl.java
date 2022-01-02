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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
public class CourseScoreServiceImpl implements CourseScoreService {

    private final BigDecimal EVERY_ATTENDANCE_SCORE=BigDecimal.ONE;

    private final CourseScoreMapper courseScoreMapper;

    private final CourseMapper courseMapper;

    private final TeacherCourseMapper teacherCourseMapper;

    private final StuExperimentMapper stuExperimentMapper;

    private final MongoTemplate mongoTemplate;

    private final StudentMapper studentMapper;

    private final GradeUtil gradeUtil;

    public CourseScoreServiceImpl(CourseScoreMapper courseScoreMapper, CourseMapper courseMapper, TeacherCourseMapper teacherCourseMapper, StuExperimentMapper stuExperimentMapper, MongoTemplate mongoTemplate, StudentMapper studentMapper, GradeUtil gradeUtil) {
        this.courseScoreMapper = courseScoreMapper;
        this.courseMapper = courseMapper;
        this.teacherCourseMapper = teacherCourseMapper;
        this.stuExperimentMapper = stuExperimentMapper;
        this.mongoTemplate = mongoTemplate;
        this.studentMapper = studentMapper;
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
        courseScore.setAttendanceScore(BigDecimal.ZERO);
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
        if (courseScore == null || courseScore.getIsActive() == 0) {
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

}
