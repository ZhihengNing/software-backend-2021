package com.yuki.experiment.framework.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yuki.experiment.framework.dto.StudentGradeDTO;
import com.yuki.experiment.framework.entity.Course;
import com.yuki.experiment.framework.entity.CourseScore;
import com.yuki.experiment.framework.entity.StuExperiment;
import com.yuki.experiment.framework.entity.StuPractice;
import com.yuki.experiment.framework.mapper.mysql.CourseMapper;
import com.yuki.experiment.framework.mapper.mysql.CourseScoreMapper;
import com.yuki.experiment.framework.mapper.mysql.StuExperimentMapper;
import com.yuki.experiment.framework.mapper.mysql.TeacherCourseMapper;
import com.yuki.experiment.framework.service.CourseScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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

    private final StuExperimentMapper stuExperimentMapper;

    private final MongoTemplate mongoTemplate;

    public CourseScoreServiceImpl(CourseScoreMapper courseScoreMapper, CourseMapper courseMapper, TeacherCourseMapper teacherCourseMapper, StuExperimentMapper stuExperimentMapper, MongoTemplate mongoTemplate) {
        this.courseScoreMapper = courseScoreMapper;
        this.courseMapper = courseMapper;
        this.teacherCourseMapper = teacherCourseMapper;
        this.stuExperimentMapper = stuExperimentMapper;
        this.mongoTemplate = mongoTemplate;
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

    private static List<BigDecimal> transfer(String scoreRatio){
        String[] split = scoreRatio.split(",");
        List<BigDecimal>list=new ArrayList<>();
        for (String s : split) {
            list.add(BigDecimal.valueOf(Double.parseDouble(s)));
        }
        return list;
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

        QueryWrapper<CourseScore> courseScoreQueryWrapper = new QueryWrapper<>();
        courseScoreQueryWrapper.eq("student_id", studentId).eq("course_id", courseId);
        CourseScore StudentGrade = courseScoreMapper.selectOne(courseScoreQueryWrapper);

        //计算考勤相关
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("id", courseId);
        Course course = courseMapper.selectOne(courseQueryWrapper);
        String scoreRatio = course.getScoreRatio();
        if (scoreRatio == null) {
            return null;
        }
        try {
            //这里可能因为没设置分数比例导致无法计算
            List<BigDecimal> transfer = transfer(scoreRatio);
            BigDecimal attendances = StudentGrade.getAttendanceScore().multiply(transfer.get(0));

            //计算实验相关
            QueryWrapper<StuExperiment> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("student_id", studentId);
            List<StuExperiment> stuExperiments = stuExperimentMapper.selectList(wrapper1);
            BigDecimal start = BigDecimal.ZERO;
            for (StuExperiment item : stuExperiments) {
                BigDecimal experimentScore = item.getExperimentScore();
                BigDecimal temp = experimentScore == null ? BigDecimal.ZERO : experimentScore;
                start = start.add(temp);
            }

            BigDecimal experiments = start.multiply(transfer.get(1));

            //计算对抗练习相关
            BigDecimal start1 = BigDecimal.ZERO;
            Criteria criteria = new Criteria();
            criteria.andOperator(
                    Criteria.where("studentId").is(studentId),
                    Criteria.where("courseId").is(courseId)
            );
            Query query = new Query(criteria);
            List<StuPractice> stuPractices = mongoTemplate
                    .find(query, StuPractice.class, "stuPractice");
            for (StuPractice item : stuPractices) {
                BigDecimal studentScore = item.getStudentScore();
                BigDecimal temp = studentScore == null ? BigDecimal.ZERO : studentScore;
                start1 = start1.add(temp);
            }

            BigDecimal practices = start1.multiply(transfer.get(2));
            //设置总分保存到数据库
            StudentGrade.setCourseScore(attendances.add(experiments).add(practices));
            UpdateWrapper<CourseScore> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("course_score", StudentGrade.getCourseScore())
                    .eq("student_id", studentId).eq("course_id", courseId);
            courseScoreMapper.update(null, updateWrapper);
            return StudentGradeDTO.builder().courseScore(StudentGrade)
                    .experimentScore(stuExperiments).practiceScore(stuPractices).build();
        }catch (Exception e){
            return null;
        }
    }

}
