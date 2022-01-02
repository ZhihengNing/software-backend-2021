package com.yuki.experiment.framework.service;

import com.yuki.experiment.framework.entity.ExperimentFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ExperimentFileService {

    List<ExperimentFile> getInfo(Integer experimentId,Integer teacherId,Integer experimentFileId);

    List<ExperimentFile> insert(List<MultipartFile> multipartFiles, Integer experimentId, Integer teacherId);



}
