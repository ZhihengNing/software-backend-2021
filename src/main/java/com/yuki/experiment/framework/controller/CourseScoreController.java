package com.yuki.experiment.framework.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import com.yuki.experiment.common.result.CommonResult;
import com.yuki.experiment.framework.entity.Course;
import com.yuki.experiment.framework.service.CourseScoreService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courseScore")
public class CourseScoreController {

    private CourseScoreService courseScoreService;

    @Autowired
    public void setCourseScoreService(CourseScoreService courseScoreService) {
        this.courseScoreService = courseScoreService;
    }

    @ApiOperation("查看学生选课的课程")
    @RequestMapping(value = "/student/{studentId}", method = RequestMethod.GET)
    public CommonResult<List<JSONObject>> getCourseInfo(@PathVariable("studentId") Integer studentId) {
        if (studentId == null) {
            return CommonResult.failed("学生Id不能为空");
        }
        List<JSONObject> courses = courseScoreService.getCourseInfoAndIsActive(studentId);
        return CommonResult.success(courses);
    }
    @ApiOperation("在前端确认激活成功之后，课程就被激活")
    @RequestMapping(value = "/enableActive",method = RequestMethod.POST)
    public CommonResult courseEnableActive(@RequestParam("id") Integer id,
                                           @RequestParam("courseId") Integer courseId) {
        if (id == null) {
            return CommonResult.failed("用户Id不能为空");
        } else if (courseId == null) {
            return CommonResult.failed("课程Id不能为空");
        }
        if(courseScoreService.setCourseActive(id, courseId)==1) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

}
