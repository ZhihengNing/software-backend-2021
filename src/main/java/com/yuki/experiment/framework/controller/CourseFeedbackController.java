package com.yuki.experiment.framework.controller;

import com.yuki.experiment.common.result.CommonResult;
import com.yuki.experiment.framework.entity.CourseFeedback;
import com.yuki.experiment.framework.service.CourseFeedbackService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courseFeedback")
public class CourseFeedbackController {


    private CourseFeedbackService courseFeedbackService;

    @Autowired
    public void setCourseFeedbackService(CourseFeedbackService courseFeedbackService) {
        this.courseFeedbackService = courseFeedbackService;
    }

    @ApiOperation("查看课程反馈")
    @RequestMapping(value = "/course/{courseId}",method = RequestMethod.GET)
    public CommonResult<List<CourseFeedback>> getFeedbackByCourse(@PathVariable Integer courseId){
        List<CourseFeedback> feedbackByCourseId = courseFeedbackService.getFeedbackByCourseId(courseId);
        return CommonResult.success(feedbackByCourseId);
    }
    @ApiOperation("查看课程反馈")
    @RequestMapping(value = "/student/{studentId}",method = RequestMethod.GET)
    public CommonResult<List<CourseFeedback>> getFeedbackByStudent(@PathVariable Integer studentId){
        List<CourseFeedback> feedbackByStudentId = courseFeedbackService.getFeedbackByStudentId(studentId);
        return CommonResult.success(feedbackByStudentId);
    }
    @ApiOperation("插入课程反馈")
    @RequestMapping(value = "",method =RequestMethod.POST)
    public CommonResult insertFeedback(@RequestBody CourseFeedback courseFeedback){
        if(courseFeedback.getStudentId()==null){
            return CommonResult.failed("学生Id不能为空");
        }
        else if(courseFeedback.getCourseId()==null){
            return CommonResult.failed("课程Id不能为空");
        }
        else if(courseFeedbackService.insertFeedback(courseFeedback)>0) {
            return CommonResult.success();
        }
        else{
            return CommonResult.failed();
        }
    }
    @ApiOperation("更新课程反馈")
    @RequestMapping(value = "",method = RequestMethod.PUT)
    public CommonResult updateFeedback(@RequestBody CourseFeedback courseFeedback) {
        if (courseFeedback.getId() == null) {
            return CommonResult.failed("课程反馈Id不能为空");
        } else if (courseFeedbackService.updateFeedback(courseFeedback) > 0) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @ApiOperation("删除课程反馈")
    @RequestMapping(value = "/{courseFeedbackId}",method = RequestMethod.DELETE)
    public CommonResult deleteFeedback(@PathVariable List<Integer> courseFeedbackId){
        if(courseFeedbackId==null){
            return CommonResult.failed("课程反馈Id不能为空");
        }
        else if(courseFeedbackService.deleteFeedback(courseFeedbackId)>0) {
            return CommonResult.success();
        }
        return CommonResult.failed();

    }



}
