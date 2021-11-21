package com.yuki.experiment.framework.service;

import com.yuki.experiment.framework.entity.CourseFeedback;

import java.util.List;

public interface CourseFeedbackService {

    List<CourseFeedback> getFeedbackByCourseId(Integer courseId);

    List<CourseFeedback>getFeedbackByStudentId(Integer studentId);

    int insertFeedback(CourseFeedback courseFeedback);

    int deleteFeedback(List<Integer> id);

    int updateFeedback(CourseFeedback courseFeedback);
}
