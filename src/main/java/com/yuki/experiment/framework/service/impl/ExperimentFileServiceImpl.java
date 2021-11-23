package com.yuki.experiment.framework.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuki.experiment.common.utils.FileUtil;
import com.yuki.experiment.framework.entity.Experiment;
import com.yuki.experiment.framework.entity.ExperimentFile;
import com.yuki.experiment.framework.mapper.ExperimentFileMapper;
import com.yuki.experiment.framework.mapper.ExperimentMapper;
import com.yuki.experiment.framework.service.ExperimentFileService;
import com.yuki.experiment.framework.service.ExperimentService;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ExperimentFileServiceImpl implements ExperimentFileService {

    private final static String PATH="course";

    @Autowired
    private ExperimentFileMapper experimentFileMapper;
    @Autowired
    private ExperimentMapper experimentMapper;


    @Override
    public List<ExperimentFile> getInfo(Integer experimentId, Integer teacherId) {
        QueryWrapper<ExperimentFile>wrapper=new QueryWrapper<>();
        wrapper.eq(experimentId!=null,"experiment_id",experimentId)
                        .eq(teacherId!=null,"teacher_id",teacherId);
        return experimentFileMapper.selectList(wrapper);
    }

    @Override
    public int insert(List<MultipartFile> multipartFiles,Integer experimentId, Integer teacherId) {
        Experiment one = experimentMapper.selectOne(new QueryWrapper<Experiment>().eq("experiment_id", experimentId));
        Integer courseId = one.getCourseId();
        if(courseId!=null) {
            Pair<String, String> twoUrl = FileUtil.generatorTwoUrl(PATH, courseId, experimentId);
            String path = twoUrl.getKey();
            String webPath = twoUrl.getValue();
            List<JSONObject> json= FileUtil.preserveFile(multipartFiles, path, webPath);
            for(JSONObject item:json){
                String data = item.getString("data");
                String name = item.getString("name");
                ExperimentFile build = ExperimentFile.builder()
                        .experimentId(experimentId)
                        .name(name)
                        .url(data)
                        .teacherId(teacherId).build();
                //保存到数据库
                experimentFileMapper.insert(build);
            }
            return 1;
        }
        return 0;
    }
}
