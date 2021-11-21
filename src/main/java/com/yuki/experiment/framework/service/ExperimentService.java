package com.yuki.experiment.framework.service;

import com.yuki.experiment.framework.entity.Experiment;

import java.util.List;

public interface ExperimentService {

    List<Experiment> getByCourseId(Integer courseId);

    List<Experiment>getByTeacherId(Integer teacherId);

    int insert(Experiment experiment);

    int update(Experiment experiment);

    int delete(List<Integer>id);
}
