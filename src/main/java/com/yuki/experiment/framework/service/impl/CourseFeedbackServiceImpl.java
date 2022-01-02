package com.yuki.experiment.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuki.experiment.framework.dto.CourseFeedbackDTO;
import com.yuki.experiment.framework.entity.CourseFeedback;
import com.yuki.experiment.framework.mapper.mysql.CourseFeedbackMapper;
import com.yuki.experiment.framework.mapper.mysql.StudentMapper;
import com.yuki.experiment.framework.service.CourseFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CourseFeedbackServiceImpl implements CourseFeedbackService {
    @Autowired
    private CourseFeedbackMapper mapper;

    @Autowired
    private StudentMapper studentMapper;

    public List<CourseFeedbackDTO>getFeedback(Integer courseId,Integer studentId) {
//        QueryWrapper<CourseFeedback> wrapper = new QueryWrapper<>();
//        wrapper.eq(courseId != null, "course_id", courseId)
//                .eq(studentId != null, "student_id", studentId);
        return mapper.getFeedback(courseId, studentId);

    }


    @Override
    public CourseFeedback insertFeedback(CourseFeedback courseFeedback) {
        if(mapper.insert(courseFeedback)>0){
            return courseFeedback;
        }
        return null;
    }

    @Override
    public int deleteFeedback(List<Integer> id) {
        return mapper.deleteBatchIds(id);
    }

    @Override
    public CourseFeedback updateFeedback(CourseFeedback courseFeedback) {
        if(mapper.updateById(courseFeedback)>0){
            return courseFeedback;
        }
        return null;
    }
}
