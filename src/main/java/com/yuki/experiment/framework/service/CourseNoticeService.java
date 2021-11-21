package com.yuki.experiment.framework.service;

import com.yuki.experiment.framework.entity.CourseNotice;

import java.util.List;

public interface CourseNoticeService {

    List<CourseNotice> getCourseNoticeByCourseId(Integer courseId);

    List<CourseNotice>getCourseNoticeByTeacherId(Integer teacherId);

    int insertCourseNoticeInfo(CourseNotice notice);

    int deleteCourseNoticeInfo(List<Integer> courseNoticeIds);

    int updateCourseNoticeInfo(CourseNotice courseNotice);
}
