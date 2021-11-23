package com.yuki.experiment.framework.service;

import com.yuki.experiment.framework.entity.ExperimentFile;

import java.util.List;

public interface ExperimentFileService {

    List<ExperimentFile> getInfo(Integer experimentId,Integer teacherId);

}
