package com.yuki.experiment.framework.service;

import com.yuki.experiment.framework.entity.CourseFile;

import java.util.List;

public interface CourseFileService {

    CourseFile getCourseFileById(Integer id);

    List<CourseFile> getCourseFileByCourseId(Integer courseId);

    List<CourseFile> getCourseFileByTeacherId(Integer teacherId);

    int insertFile(String fileName,Integer courseId,Integer teacherId,String url);

    int deleteFile(List<Integer> id);
}
