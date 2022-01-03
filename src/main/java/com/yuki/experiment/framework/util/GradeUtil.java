package com.yuki.experiment.framework.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yuki.experiment.framework.dto.StudentGradeDTO;
import com.yuki.experiment.framework.entity.*;
import com.yuki.experiment.framework.mapper.mysql.CourseMapper;
import com.yuki.experiment.framework.mapper.mysql.CourseScoreMapper;
import com.yuki.experiment.framework.mapper.mysql.StuExperimentMapper;
import com.yuki.experiment.framework.mapper.mysql.StudentMapper;
import org.springframework.data.mongodb.core.MongoAction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class GradeUtil {

    private final StudentMapper studentMapper;

    private final CourseScoreMapper courseScoreMapper;

    private final CourseMapper courseMapper;

    private final StuExperimentMapper stuExperimentMapper;

    private final MongoTemplate mongoTemplate;


    public GradeUtil(StudentMapper studentMapper, CourseScoreMapper courseScoreMapper, CourseMapper courseMapper, StuExperimentMapper stuExperimentMapper, MongoTemplate mongoTemplate) {
        this.studentMapper = studentMapper;
        this.courseScoreMapper = courseScoreMapper;
        this.courseMapper = courseMapper;
        this.stuExperimentMapper = stuExperimentMapper;
        this.mongoTemplate = mongoTemplate;
    }

    private static List<BigDecimal> transfer(String scoreRatio){
        if(scoreRatio==null){
            return Arrays.asList(BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO);
        }
        String[] split = scoreRatio.split(",");
        List<BigDecimal>list=new ArrayList<>();
        for (String s : split) {
            list.add(BigDecimal.valueOf(Double.parseDouble(s)));
        }
        return list;
    }

    public StudentGradeDTO getGrade(Integer studentId, Integer courseId){
        String name = studentMapper.selectOne(new QueryWrapper<Student>()
                .eq("id", studentId)).getName();

        QueryWrapper<CourseScore> courseScoreQueryWrapper = new QueryWrapper<>();
        courseScoreQueryWrapper.eq("student_id", studentId).eq("course_id", courseId);
        CourseScore StudentGrade = courseScoreMapper.selectOne(courseScoreQueryWrapper);
        if(StudentGrade==null||StudentGrade.getIsActive()==0){
            return null;
        }

        //计算考勤相关
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("id", courseId);
        Course course = courseMapper.selectOne(courseQueryWrapper);
        String scoreRatio = course.getScoreRatio();

        //这里可能因为没设置分数比例导致无法计算
        List<BigDecimal> transfer = transfer(scoreRatio);
        BigDecimal attendanceScore = StudentGrade.getAttendanceScore();
        BigDecimal tempAttendance=attendanceScore==null?BigDecimal.ZERO:attendanceScore;
        BigDecimal attendances = tempAttendance.multiply(transfer.get(0));

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
        return StudentGradeDTO.builder().take(StudentGrade)
                .experiments(stuExperiments).practices(stuPractices)
                .studentId(studentId).studentName(name).build();

    }
}