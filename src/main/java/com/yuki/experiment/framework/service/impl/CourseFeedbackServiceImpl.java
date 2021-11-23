package com.yuki.experiment.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

    public List<CourseFeedback>getFeedback(Integer courseId,Integer studentId){
        QueryWrapper<CourseFeedback>wrapper=new QueryWrapper<>();
        wrapper.eq(courseId!=null,"course_id",courseId)
        .eq(studentId!=null,"student_id",studentId);
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
