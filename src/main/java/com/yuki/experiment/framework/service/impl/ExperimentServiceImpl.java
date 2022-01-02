package com.yuki.experiment.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuki.experiment.framework.entity.Experiment;
import com.yuki.experiment.framework.mapper.mysql.ExperimentMapper;
import com.yuki.experiment.framework.service.ExperimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ExperimentServiceImpl implements ExperimentService {
    @Autowired
    private ExperimentMapper mapper;

    @Override
    public List<Experiment> getExperiment(Integer courseId,Integer teacherId,Integer experimentId) {
        QueryWrapper<Experiment>wrapper=new QueryWrapper<>();
        wrapper.eq(courseId!=null,"course_id",courseId)
                .eq(teacherId!=null,"teacher_id",teacherId)
                .eq(experimentId!=null,"id",experimentId);
        return mapper.selectList(wrapper);
    }

    @Override
    public Experiment insert(Experiment experiment) {
        if(mapper.insert(experiment)>0){
            return experiment;
        }
        return null;
    }

    @Override
    public Experiment update(Experiment experiment) {
        if (mapper.updateById(experiment) > 0) {
            return experiment;
        }
        return null;
    }

    @Override
    public int delete(List<Integer> id) {
        return mapper.deleteBatchIds(id);
    }
}
