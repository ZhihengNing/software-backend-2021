package com.yuki.experiment.framework.service;

import com.yuki.experiment.framework.entity.Practice;
import com.yuki.experiment.framework.entity.Problem;

import java.util.List;

public interface PracticeService {

    List<Practice> getAll(Integer courseId,Integer teacherId);

    Practice getOneById(String practiceId);

    Practice addSomeProblems(Practice practice);

    Practice update(Practice practice);

    Practice random(Integer courseId);

    void insert();
}
