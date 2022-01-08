package com.yuki.experiment.framework.service;

import com.alibaba.fastjson.JSONObject;
import com.yuki.experiment.framework.dto.StudentGradeDTO;
import com.yuki.experiment.framework.entity.CourseScore;

import java.math.BigDecimal;
import java.util.List;

public interface CourseScoreService {

    List<JSONObject> getCourseInfoAndIsActive(Integer studentId);

    int setCourseActive(Integer studentId,Integer courseId);

   CourseScore signIn(Integer studentId, Integer courseId);

    StudentGradeDTO getStudentGrade(Integer studentId, Integer courseId);

    CourseScore uploadPracticeGrade(Integer studentId, Integer courseId, BigDecimal grade);

    boolean judgeSignIn(Integer studentId,Integer courseId);


}
