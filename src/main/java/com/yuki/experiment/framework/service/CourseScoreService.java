package com.yuki.experiment.framework.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface CourseScoreService {

    List<JSONObject> getCourseInfoAndIsActive(Integer studentId);

    int setCourseActive(Integer studentId,Integer courseId);
}
