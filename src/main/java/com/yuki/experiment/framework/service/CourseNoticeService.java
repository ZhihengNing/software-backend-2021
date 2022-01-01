package com.yuki.experiment.framework.service;

import com.yuki.experiment.framework.dto.CourseNoticeTeacherDTO;
import com.yuki.experiment.framework.entity.CourseNotice;

import java.util.Date;
import java.util.List;

public interface CourseNoticeService {

    List<CourseNotice> getCourseNotice(Integer courseId,Integer teacherId,Integer courseNoticeId);

    int insertCourseNoticeInfo(CourseNotice notice);

    int deleteCourseNoticeInfo(List<Integer> courseNoticeIds);

    CourseNotice updateCourseNoticeInfo(CourseNotice courseNotice);

    List<CourseNoticeTeacherDTO> fuzzyQueryCourseNoticeInfo(String keyword);

    List<CourseNotice> queryByTime(Date beginDate,Date endDate);
}
