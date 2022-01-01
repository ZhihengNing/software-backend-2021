package com.yuki.experiment.framework.service;

import com.yuki.experiment.framework.entity.Practice;

import java.util.List;

public interface PracticeService {

    List<Practice> getAll(Integer courseId,Integer teacherId);

    Practice getOneById(String practiceId);

    Practice insert(Practice jsonObject);

    Practice update(Practice practice);

    void insert();
}
