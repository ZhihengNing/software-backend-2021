package com.yuki.experiment.framework.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.oracle.tools.packager.Log;
import com.yuki.experiment.common.utils.FileUtil;
import com.yuki.experiment.framework.entity.StuExperiment;
import com.yuki.experiment.framework.entity.StudentUploadFile;
import com.yuki.experiment.framework.mapper.StuExperimentMapper;
import com.yuki.experiment.framework.mapper.StudentUploadFileMapper;
import com.yuki.experiment.framework.service.StuExperimentService;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
@Service
public class StuExperimentServiceImpl implements StuExperimentService {

    private final static String experimentFileUploadPath = "experiment";

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
        QueryWrapper<StuExperiment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", studentId);
        return stuExperimentMapper.selectList(queryWrapper);
    }

    @Override
    public List<StuExperiment> getStuExperimentByExperimentId(Integer experimentId) {
        QueryWrapper<StuExperiment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("experiment_id", experimentId);
        return stuExperimentMapper.selectList(queryWrapper);
    }

    @Override
    public StuExperiment getStuExperiment(Integer studentId, Integer experimentId) {
        QueryWrapper<StuExperiment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("experiment_id", experimentId).eq("student_id", studentId);
        return stuExperimentMapper.selectOne(queryWrapper);
    }

    @Override
    public int insert(MultipartFile multipartFile, StuExperiment stuExperiment) {
        Integer experimentId = stuExperiment.getExperimentId();
        Integer studentId = stuExperiment.getStudentId();
        Pair<String, String> twoUrl = FileUtil.generatorTwoUrl(experimentFileUploadPath, experimentId, studentId);
        String path = twoUrl.getKey();
        String webPath = twoUrl.getValue();
        //保存到服务器
        JSONObject json = FileUtil.preserveFile(multipartFile, path, webPath);
        String data = json.getString("data");
        String name = json.getString("name");
        StudentUploadFile file = new StudentUploadFile();
        file.setName(name);
        file.setUrl(data);
        Integer fileId;
        if ((fileId = file.getId()) != null) {
            stuExperiment.setFileId(fileId);
            Log.info(name + "成功保存到数据库！");
            //插入到stu_experiment表进行保存
            if (stuExperimentMapper.insert(stuExperiment) > 0) {
                //插入到student_upload_file表进行保存
                return studentUploadFileMapper.insert(file);
            }
        }
        return 0;
    }
    @Transactional
    @Override
    public int update(MultipartFile multipartFile, Integer studentId,Integer experimentId,String jobContent) {
        Pair<String, String> twoUrl = FileUtil.generatorTwoUrl(experimentFileUploadPath, experimentId, studentId);
        String path = twoUrl.getKey();
        String webPath = twoUrl.getValue();
        //在服务器删除原来文件的url
        Integer fileId = stuExperimentMapper.selectOne(new QueryWrapper<StuExperiment>()
                .eq("student_id", studentId)
                .eq("experiment_id", experimentId)).getFileId();
        String url=stuExperimentMapper.getUrl(studentId, experimentId);
        if (url != null) {
            FileUtil.deleteFile(url);
        }
        //把新的文件url存入服务器
        JSONObject json = FileUtil.preserveFile(multipartFile, path, webPath);
        String data = json.getString("data");
        String name = json.getString("name");
        //这里要更新student_upload_file
        StudentUploadFile temp = new StudentUploadFile();
        temp.setId(fileId);
        temp.setName(name);
        temp.setUrl(data);
        if (studentUploadFileMapper.updateById(temp) > 0) {
            Log.info(name + "成功替换原文件,存到数据库中");
            //这里更新stu_experiment
            UpdateWrapper<StuExperiment> wrapper = new UpdateWrapper<>();
            //.set(stuExperiment.getExperimentScore() == null, "experiment_score", null)
            //.set(stuExperiment.getFileId() == null, "file_id", null)
            wrapper.set(jobContent != null, "job_content", jobContent)
                    .eq("student_id", studentId)
                    .eq("experiment_id", experimentId);
            return stuExperimentMapper.update(null, wrapper);
        }
        return 0;
    }

    @Override
    public int uploadGrade(Integer studentId, Integer experimentId, BigDecimal grade) {
        StuExperiment build = StuExperiment.builder().experimentId(experimentId).studentId(studentId).experimentScore(grade).build();
        return stuExperimentMapper.updateById(build);
    }
}