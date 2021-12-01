package com.yuki.experiment.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yuki.experiment.framework.entity.CourseNotice;
import com.yuki.experiment.framework.mapper.CourseNoticeMapper;
import com.yuki.experiment.framework.service.CourseNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CourseNoticeServiceImpl implements CourseNoticeService {
    @Autowired
    private CourseNoticeMapper mapper;

    @Override
    public List<CourseNotice> getCourseNotice(Integer courseId,Integer teacherId) {
        return mapper.selectList(new QueryWrapper<CourseNotice>()
                .eq(courseId != null, "course_id", courseId)
                .eq(teacherId != null, "teacher_id", teacherId));
    }

    @Override
    public int insertCourseNoticeInfo(CourseNotice notice) {
        return mapper.insert(notice);
    }

    @Override
    public int deleteCourseNoticeInfo(List<Integer> courseNoticeIds) {
        return mapper.deleteBatchIds(courseNoticeIds);
    }

    @Override
    public CourseNotice updateCourseNoticeInfo(CourseNotice courseNotice) {
        mapper.updateById(courseNotice);
        return mapper.selectById(courseNotice.getId());
    }
}
