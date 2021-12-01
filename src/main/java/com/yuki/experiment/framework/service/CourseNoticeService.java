package com.yuki.experiment.framework.service;

import com.yuki.experiment.framework.entity.CourseNotice;

import java.util.List;

public interface CourseNoticeService {

    List<CourseNotice> getCourseNotice(Integer courseId,Integer teacherId);

    int insertCourseNoticeInfo(CourseNotice notice);

    int deleteCourseNoticeInfo(List<Integer> courseNoticeIds);

    CourseNotice updateCourseNoticeInfo(CourseNotice courseNotice);
}
