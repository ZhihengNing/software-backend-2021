package com.yuki.experiment.framework.service;

import com.yuki.experiment.framework.entity.StuExperiment;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface StuExperimentService {

    List<StuExperiment> getStuExperiment(Integer studentId,Integer experimentId);

    int insert(MultipartFile multipartFile,StuExperiment stuExperiment);

    int update(MultipartFile multipartFile,Integer studentId,Integer experimentId,String jobContent);

    int uploadGrade(Integer studentId, Integer experimentId, BigDecimal grade);

    BigDecimal getStudentGrade(Integer studentId,Integer courseId);

}
