package com.yuki.experiment.framework.service;

import com.yuki.experiment.framework.dto.CourseFeedbackDTO;
import com.yuki.experiment.framework.entity.CourseFeedback;

import java.util.List;

public interface CourseFeedbackService {

    List<CourseFeedbackDTO> getFeedback(Integer studentId, Integer courseId);

    CourseFeedback insertFeedback(CourseFeedback courseFeedback);

   int deleteFeedback(List<Integer> id);

    CourseFeedback updateFeedback(CourseFeedback courseFeedback);
}
