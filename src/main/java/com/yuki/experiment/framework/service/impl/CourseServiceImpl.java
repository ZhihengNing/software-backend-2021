package com.yuki.experiment.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yuki.experiment.framework.dto.CourseRatioDTO;
import com.yuki.experiment.framework.dto.StudentGradeDTO;
import com.yuki.experiment.framework.entity.Course;
import com.yuki.experiment.framework.entity.CourseScore;
import com.yuki.experiment.framework.entity.TeacherCourse;
import com.yuki.experiment.framework.mapper.mysql.CourseMapper;
import com.yuki.experiment.framework.mapper.mysql.CourseScoreMapper;
import com.yuki.experiment.framework.mapper.mysql.TeacherCourseMapper;
import com.yuki.experiment.framework.service.CourseService;
import com.yuki.experiment.framework.util.GradeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseMapper courseMapper;

    private final CourseScoreMapper courseScoreMapper;

    private final TeacherCourseMapper teacherCourseMapper;

    private final GradeUtil gradeUtil;

    public CourseServiceImpl(CourseMapper courseMapper, CourseScoreMapper courseScoreMapper, TeacherCourseMapper teacherCourseMapper, GradeUtil gradeUtil) {
        this.courseMapper = courseMapper;
        this.courseScoreMapper = courseScoreMapper;
        this.teacherCourseMapper = teacherCourseMapper;
        this.gradeUtil = gradeUtil;
    }

    @Override
    public List<Course> allCourse() {
        List<Course> courses = courseMapper.selectList(null);
        return courses;
    }

    @Override
    public int insert(Course course) {
        courseMapper.insert(course);
        TeacherCourse teacherCourse = new TeacherCourse();
        teacherCourse.setCourseId(course.getId());

        teacherCourse.setCharacterName("责任教师");
        teacherCourse.setTeacherId(course.getTeacherId());
        teacherCourseMapper.insert(teacherCourse);

        return 1;
    }

    @Override
    public List<Course> getCourseInfoByTeacher(Integer teacherId, Integer courseId) {
        QueryWrapper<TeacherCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id", teacherId)
                .eq(courseId != null, "course_id", courseId);
        List<TeacherCourse> teacherCourses = teacherCourseMapper.selectList(queryWrapper);
        List<Course> list = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        for (TeacherCourse item : teacherCourses) {
            set.add(item.getCourseId());
        }
        for (Integer item : set) {
            QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
            courseQueryWrapper.eq("id", item);
            Course course = courseMapper.selectOne(courseQueryWrapper);
            list.add(course);
        }
        return list;
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

    @Override
    public List<StudentGradeDTO> getTakeStudent(Integer courseId) {

        QueryWrapper<CourseScore> courseScoreQueryWrapper = new QueryWrapper<>();
        courseScoreQueryWrapper.eq("course_id", courseId);
        List<CourseScore> courseScores = courseScoreMapper.selectList(courseScoreQueryWrapper);
        List<StudentGradeDTO> list = new ArrayList<>();
        courseScores.parallelStream().forEach((s) ->
                list.add(gradeUtil.getGrade(s.getStudentId(), courseId))
        );
        return list;
    }

}
