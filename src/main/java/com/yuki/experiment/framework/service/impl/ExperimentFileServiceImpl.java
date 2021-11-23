package com.yuki.experiment.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuki.experiment.framework.entity.ExperimentFile;
import com.yuki.experiment.framework.mapper.ExperimentFileMapper;
import com.yuki.experiment.framework.service.ExperimentFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperimentFileServiceImpl implements ExperimentFileService {
    @Autowired
    ExperimentFileMapper mapper;
    @Override
    public List<ExperimentFile> getInfo(Integer experimentId, Integer teacherId) {
        QueryWrapper<ExperimentFile>wrapper=new QueryWrapper<>();
        wrapper.eq(experimentId!=null,"experiment_id",experimentId)
                        .eq(teacherId!=null,"teacher_id",teacherId);
        return mapper.selectList(wrapper);
    }
}
