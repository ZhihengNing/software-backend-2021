package com.yuki.experiment.framework.service;

import com.yuki.experiment.framework.entity.Experiment;

import java.util.List;

public interface ExperimentService {
    List<Experiment> getExperiment(Integer courseId,Integer teacherId,Integer experimentId);

    int insert(Experiment experiment);

    int update(Experiment experiment);

    int delete(List<Integer>id);
}
