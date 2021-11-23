package com.yuki.experiment.framework.service;

import com.yuki.experiment.framework.entity.CourseFeedback;

import java.util.List;

public interface CourseFeedbackService {

    List<CourseFeedback> getFeedback(Integer studentId,Integer courseId);

    int insertFeedback(CourseFeedback courseFeedback);

    int deleteFeedback(List<Integer> id);

    int updateFeedback(CourseFeedback courseFeedback);
}
