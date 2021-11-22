package com.yuki.experiment.framework.service;

import com.yuki.experiment.framework.entity.StuExperiment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StuExperimentService {
    List<StuExperiment> getStuExperimentByStudentId(Integer studentId);

    List<StuExperiment>getStuExperimentByExperimentId(Integer experimentId);

    StuExperiment getStuExperiment(Integer studentId,Integer experimentId);

    int insert(MultipartFile multipartFile,StuExperiment stuExperiment);

    int update(StuExperiment stuExperiment);

}
