package com.yuki.experiment.framework.service;

import com.yuki.experiment.framework.dto.StuExperimentDTO;
import com.yuki.experiment.framework.dto.StudentGradeDTO;
import com.yuki.experiment.framework.entity.StuExperiment;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface StuExperimentService {

    List<StuExperimentDTO> getStuExperiment(Integer studentId, Integer experimentId);

    int insert(MultipartFile multipartFile,StuExperiment stuExperiment);

    StuExperiment update(MultipartFile multipartFile, Integer studentId, Integer experimentId, String jobContent);

    int uploadGrade(Integer studentId, Integer experimentId, BigDecimal grade);

}
