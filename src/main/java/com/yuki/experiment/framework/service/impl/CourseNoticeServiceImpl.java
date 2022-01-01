package com.yuki.experiment.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuki.experiment.common.utils.SimilarityUtil;
import com.yuki.experiment.framework.dto.CourseNoticeTeacherDTO;
import com.yuki.experiment.framework.entity.CourseNotice;
import com.yuki.experiment.framework.mapper.mysql.CourseNoticeMapper;
import com.yuki.experiment.framework.service.CourseNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseNoticeServiceImpl implements CourseNoticeService {
    @Autowired
    private CourseNoticeMapper courseNoticeMapper;

    @Override
    public List<CourseNotice> getCourseNotice(Integer courseId,Integer teacherId,Integer courseNoticeId) {
        return courseNoticeMapper.selectList(new QueryWrapper<CourseNotice>()
                .eq(courseId != null, "course_id", courseId)
                .eq(teacherId != null, "teacher_id", teacherId)
                .eq(courseNoticeId!=null,"id",courseNoticeId));
    }

    @Override
    public int insertCourseNoticeInfo(CourseNotice notice) {
        return courseNoticeMapper.insert(notice);
    }

    @Override
    public int deleteCourseNoticeInfo(List<Integer> courseNoticeIds) {
        return courseNoticeMapper.deleteBatchIds(courseNoticeIds);
    }

    @Override
    public CourseNotice updateCourseNoticeInfo(CourseNotice courseNotice) {
        courseNoticeMapper.updateById(courseNotice);
        return courseNoticeMapper.selectById(courseNotice.getId());
    }

    @Override
    public List<CourseNoticeTeacherDTO> fuzzyQueryCourseNoticeInfo(String keyword) {
//        QueryWrapper<CourseNotice>wrapper=new QueryWrapper<>();
//        wrapper.like("title",keyword)
//                .or()
//                .like("content",keyword);
//        return courseNoticeMapper.selectList(wrapper);
        List<CourseNoticeTeacherDTO> list = courseNoticeMapper.getCourseNoticeTeacher();
        return list.parallelStream().filter((s) -> {
            double v = SimilarityUtil.similarScoreCos(s.getTitle(), keyword);
            double v1 = SimilarityUtil.similarScoreCos(s.getContent(), keyword);
            double v2 = SimilarityUtil.similarScoreCos(s.getName(), keyword);
            return v>0.3 || v1 > 0.3 || v2 > 0.3;
        }).collect(Collectors.toList());
    }

    @Override
    public List<CourseNotice> queryByTime(Date beginDate, Date endDate) {
        QueryWrapper<CourseNotice> wrapper = new QueryWrapper<>();
        wrapper.between("create_time", beginDate, endDate).or()
                .between("update_time", beginDate, endDate);
        return courseNoticeMapper.selectList(wrapper);
    }
}
