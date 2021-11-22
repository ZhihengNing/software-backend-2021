package com.yuki.experiment.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yuki.experiment.common.utils.FileUtil;
import com.yuki.experiment.framework.entity.ExperimentFile;
import com.yuki.experiment.framework.entity.StuExperiment;
import com.yuki.experiment.framework.entity.StudentUploadFile;
import com.yuki.experiment.framework.mapper.ExperimentFileMapper;
import com.yuki.experiment.framework.mapper.StuExperimentMapper;
import com.yuki.experiment.framework.mapper.StudentUploadFileMapper;
import com.yuki.experiment.framework.service.StuExperimentService;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Service
public class StuExperimentServiceImpl implements StuExperimentService {

    private final static String experimentFileUploadPath="experiment";

    private StuExperimentMapper stuExperimentMapper;

    private StudentUploadFileMapper studentUploadFileMapper;
    @Autowired
    public void setStuExperimentMapper(StuExperimentMapper stuExperimentMapper) {
        this.stuExperimentMapper = stuExperimentMapper;
    }
    @Autowired
    public void setStudentUploadFileMapper(StudentUploadFileMapper studentUploadFileMapper) {
        this.studentUploadFileMapper = studentUploadFileMapper;
    }

    @Override
    public List<StuExperiment> getStuExperimentByStudentId(Integer studentId) {
        QueryWrapper<StuExperiment>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("student_id",studentId);
        return stuExperimentMapper.selectList(queryWrapper);
    }

    @Override
    public List<StuExperiment> getStuExperimentByExperimentId(Integer experimentId) {
        QueryWrapper<StuExperiment>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("experiment_id",experimentId);
        return stuExperimentMapper.selectList(queryWrapper);
    }

    @Override
    public StuExperiment getStuExperiment(Integer studentId, Integer experimentId) {
        QueryWrapper<StuExperiment>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("experiment_id",experimentId).eq("student_id",studentId);
        return stuExperimentMapper.selectOne(queryWrapper);
    }

    @Override
    public int insert(MultipartFile multipartFile,StuExperiment stuExperiment) {
        Integer experimentId = stuExperiment.getExperimentId();
        Integer studentId = stuExperiment.getStudentId();
        Pair<String, String> twoUrl = FileUtil.generatorTwoUrl(experimentFileUploadPath, experimentId, studentId);
        String path = twoUrl.getKey();
        String webPath = twoUrl.getValue();
        //保存到服务器
        FileUtil.preserveFile(multipartFile, path, webPath);

        StudentUploadFile file=new StudentUploadFile();
        file.setName(multipartFile.getOriginalFilename());
        file.setUrl(webPath);
        //插入到student_upload_file表进行保存
        studentUploadFileMapper.insert(file);
        Integer fileId;
        if((fileId=file.getId())!=null){
            stuExperiment.setFileId(fileId);
            //插入到stu_experiment表进行保存
            return stuExperimentMapper.insert(stuExperiment);
        }
        return 0;
    }

    @Override
    public int update(StuExperiment stuExperiment) {
        UpdateWrapper<StuExperiment> wrapper = new UpdateWrapper<>();
        wrapper.set(stuExperiment.getExperimentScore() == null, "experiment_score", null)
                .set(stuExperiment.getFileId() == null, "file_id", null)
                .set(stuExperiment.getJobContent() == null, "job_content", null)
                .eq("student_id", stuExperiment.getStudentId())
                .eq("experiment_id", stuExperiment.getExperimentId());
        return stuExperimentMapper.update(stuExperiment, wrapper);
    }
}
