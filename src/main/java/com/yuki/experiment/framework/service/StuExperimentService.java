package com.yuki.experiment.framework.service;

import com.alibaba.fastjson.JSONObject;
import com.yuki.experiment.framework.dto.StudentGradeDTO;
import com.yuki.experiment.framework.entity.Experiment;
import com.yuki.experiment.framework.entity.StuExperiment;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface StuExperimentService {

    List<StuExperiment> getStuExperiment(Integer studentId,Integer experimentId);

    int insert(MultipartFile multipartFile,StuExperiment stuExperiment);

    StuExperiment update(MultipartFile multipartFile, Integer studentId, Integer experimentId, String jobContent);

    int uploadGrade(Integer studentId, Integer experimentId, BigDecimal grade);

    StudentGradeDTO getStudentGrade(Integer studentId, Integer courseId);

}
