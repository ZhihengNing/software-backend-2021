package com.yuki.experiment.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuki.experiment.common.utils.FileUtil;
import com.yuki.experiment.framework.entity.CourseFile;
import com.yuki.experiment.framework.mapper.CourseFileMapper;
import com.yuki.experiment.framework.service.CourseFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseFileServiceImpl implements CourseFileService {
    @Autowired
    private CourseFileMapper mapper;

    @Override
    public CourseFile getCourseFileById(Integer id) {
        return mapper.selectOne(new QueryWrapper<CourseFile>().eq("id", id));
    }

    @Override
    public List<CourseFile> getCourseFileByCourseId(Integer courseId) {
        QueryWrapper<CourseFile>wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        return mapper.selectList(wrapper);
    }

    @Override
    public List<CourseFile> getCourseFileByTeacherId(Integer teacherId) {
        QueryWrapper<CourseFile> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId);
        return mapper.selectList(wrapper);
    }

    @Override
    public int insertFile(String fileName, Integer courseId, Integer teacherId,String url) {
        CourseFile file = new CourseFile();
        file.setCourseId(courseId);
        file.setTeacherId(teacherId);
        file.setName(fileName);
        file.setUrl(url);
        return mapper.insert(file);
    }

    @Override
    public int deleteFile(List<Integer> id) {
        for (Integer item : id) {
            CourseFile courseFile = mapper.selectOne(new QueryWrapper<CourseFile>().eq("id", item));
            if (courseFile != null) {
                System.out.println(courseFile.getUrl());
                FileUtil.deleteFile(courseFile.getUrl());
                mapper.deleteById(item);
            }
        }
        return 1;
    }
}
