package com.yuki.experiment.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yuki.experiment.common.utils.FileUtil;
import com.yuki.experiment.framework.dto.FileInfoDTO;
import com.yuki.experiment.framework.dto.StuExperimentDTO;
import com.yuki.experiment.framework.entity.StuExperiment;
import com.yuki.experiment.framework.mapper.mysql.StuExperimentMapper;
import com.yuki.experiment.framework.service.StuExperimentService;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.Yaml;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class StuExperimentServiceImpl implements StuExperimentService {

    private final static String experimentFileUploadPath = "experiment";

    private StuExperimentMapper stuExperimentMapper;

    @Autowired
    public void setStuExperimentMapper(StuExperimentMapper stuExperimentMapper) {
        this.stuExperimentMapper = stuExperimentMapper;
    }


    @Override
    public List<StuExperimentDTO> getStuExperiment(Integer studentId, Integer experimentId) {
        return stuExperimentMapper.getStudentWork(experimentId,studentId);
    }

    @Override
    public int insert(MultipartFile multipartFile, StuExperiment stuExperiment) {
        Integer experimentId = stuExperiment.getExperimentId();
        Integer studentId = stuExperiment.getStudentId();
        Pair<String, String> twoUrl = FileUtil.generatorTwoUrl(experimentFileUploadPath, experimentId, studentId);
        String path = twoUrl.getKey();
        String webPath = twoUrl.getValue();
        //保存到服务器
        FileInfoDTO fileInfoDTO = FileUtil.preserveFile(multipartFile, path, webPath);
        if(fileInfoDTO!=null) {
            String url = fileInfoDTO.getFileUrl();
            String name = fileInfoDTO.getFileName();

            stuExperiment.setUrl(url);
            log.info(name + "成功保存到数据库！");
            //插入到stu_experiment表进行保存
            return stuExperimentMapper.insert(stuExperiment);
        }
        return 0;
    }


    @Override
    public StuExperiment update(MultipartFile multipartFile, Integer studentId, Integer experimentId, String jobContent) {
        Pair<String, String> twoUrl = FileUtil.generatorTwoUrl(experimentFileUploadPath, experimentId, studentId);
        String path = twoUrl.getKey();
        String webPath = twoUrl.getValue();
        //在服务器删除原来文件的url
        String originalUrl = stuExperimentMapper.getUrl(studentId, experimentId);
        if (originalUrl != null) {
            FileUtil.deleteFile(originalUrl);
        }
        //把新的文件url存入服务器
        FileInfoDTO fileInfoDTO = FileUtil.preserveFile(multipartFile, path, webPath);
        StuExperiment.StuExperimentBuilder builder = StuExperiment.builder();
        if (!StringUtils.isBlank(jobContent)) {
            builder.jobContent(jobContent);
        }
        if (fileInfoDTO != null) {
            String url = fileInfoDTO.getFileUrl();
            String name = fileInfoDTO.getFileName();
            log.info(name + "成功替换原文件,存到数据库中");
            //这里更新stu_experiment
            builder.url(url);
        }
        StuExperiment build = builder.build();
        UpdateWrapper<StuExperiment> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("student_id", studentId).eq("experiment_id", experimentId);
        stuExperimentMapper.update(build, updateWrapper);
        return stuExperimentMapper.selectOne(new QueryWrapper<StuExperiment>()
                .eq("student_id", studentId)
                .eq("experiment_id", experimentId));
    }

    @Override
    public int uploadGrade(Integer studentId, Integer experimentId, BigDecimal grade) {
        StuExperiment build = StuExperiment.builder()
                .experimentScore(grade)
                .build();
        UpdateWrapper<StuExperiment> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("student_id", studentId).eq("experiment_id", experimentId);
        return stuExperimentMapper.update(build, updateWrapper);
    }



}
