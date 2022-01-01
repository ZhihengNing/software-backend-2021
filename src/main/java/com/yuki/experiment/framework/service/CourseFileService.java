package com.yuki.experiment.framework.service;

import com.yuki.experiment.framework.entity.CourseFile;

import java.util.List;

public interface CourseFileService {

    List<CourseFile> getCourseFiles(Integer courseId,Integer teacherId,Integer courseFileId);


    int insertFile(String fileName,Integer courseId,Integer teacherId,String url);

    int deleteFile(List<Integer> id);
}
