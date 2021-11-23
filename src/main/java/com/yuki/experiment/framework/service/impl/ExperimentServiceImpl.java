package com.yuki.experiment.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuki.experiment.framework.entity.Experiment;
import com.yuki.experiment.framework.mapper.ExperimentMapper;
import com.yuki.experiment.framework.service.ExperimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ExperimentServiceImpl implements ExperimentService {
    @Autowired
    private ExperimentMapper mapper;

    @Override
    public List<Experiment> getExperiment(Integer courseId,Integer teacherId) {
        QueryWrapper<Experiment>wrapper=new QueryWrapper<>();
        wrapper.eq(courseId!=null,"course_id",courseId)
                .eq(teacherId!=null,"teacher_id",teacherId);
        return mapper.selectList(wrapper);
    }

    @Override
    public int insert(Experiment experiment) {
        return mapper.insert(experiment);
    }

    @Override
    public int update(Experiment experiment) {
        return mapper.updateById(experiment);
    }

    @Override
    public int delete(List<Integer> id) {
        return mapper.deleteBatchIds(id);
    }
}
