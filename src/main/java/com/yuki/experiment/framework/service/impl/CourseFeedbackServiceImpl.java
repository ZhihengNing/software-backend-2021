package com.yuki.experiment.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yuki.experiment.framework.entity.CourseFeedback;
import com.yuki.experiment.framework.mapper.CourseFeedbackMapper;
import com.yuki.experiment.framework.service.CourseFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CourseFeedbackServiceImpl implements CourseFeedbackService {
    @Autowired
    private CourseFeedbackMapper mapper;
    @Override
    public List<CourseFeedback> getFeedbackByCourseId(Integer courseId) {
        QueryWrapper<CourseFeedback>wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        return mapper.selectList(wrapper);
    }

    @Override
    public List<CourseFeedback> getFeedbackByStudentId(Integer studentId) {
        QueryWrapper<CourseFeedback>wrapper=new QueryWrapper<>();
        wrapper.eq("student_id",studentId);
        return mapper.selectList(wrapper);
    }

    @Override
    public int insertFeedback(CourseFeedback courseFeedback) {
        return mapper.insert(courseFeedback);
    }

    @Override
    public int deleteFeedback(List<Integer> id) {
        return mapper.deleteBatchIds(id);
    }

    @Override
    public int updateFeedback(CourseFeedback courseFeedback) {
        return mapper.updateById(courseFeedback);
    }
}
