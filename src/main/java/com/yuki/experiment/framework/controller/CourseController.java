package com.yuki.experiment.framework.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuki.experiment.common.result.CommonResult;
import com.yuki.experiment.framework.entity.Course;
import com.yuki.experiment.framework.entity.Teacher;
import com.yuki.experiment.framework.mapper.TeacherMapper;
import com.yuki.experiment.framework.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course")
@Slf4j
public class CourseController {

    private CourseService courseService;

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @Autowired
    private TeacherMapper mapper;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public CommonResult<Boolean> insert(@RequestBody JSONObject json) {
        String name = json.getString("name");
        String place = json.getString("place");
        String openPeriod = json.getString("openPeriod");
        String teacherName = json.getString("teacherName");
        Integer credit = json.getInteger("credit");
        String college = json.getString("college");

        Course course = new Course();
        course.setName(name);
        course.setPlace(place);
        course.setOpenPeriod(openPeriod);
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", teacherName);
        course.setTeacherId(mapper.selectOne(queryWrapper).getId());
        course.setCredit(credit);
        course.setCollege(college);
        courseService.insert(course);
        return CommonResult.success(true);
    }

}
