package com.yuki.experiment.framework.controller;

import com.yuki.experiment.common.result.CommonResult;
import com.yuki.experiment.framework.entity.CourseNotice;
import com.yuki.experiment.framework.service.CourseNoticeService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courseNotice")
@Slf4j
public class CourseNoticeController {
    private CourseNoticeService courseNoticeService;

    @Autowired
    public void setCourseNoticeService(CourseNoticeService courseNoticeService) {
        this.courseNoticeService = courseNoticeService;
    }

    @ApiOperation("查看课程公告")
    @RequestMapping(value = "/course/{courseId}",method = RequestMethod.GET)
    public CommonResult<List<CourseNotice>>getInfoByCourseId(@PathVariable("courseId") Integer courseId){
        if(courseId==null){
            return CommonResult.failed("课程ID不能为空");
        }
        return CommonResult.success(courseNoticeService.getCourseNoticeByCourseId(courseId));
    }
    @ApiOperation("查看课程公告")
    @RequestMapping(value = "/teacher/{teacherId}",method = RequestMethod.GET)
    public CommonResult<List<CourseNotice>>getInfoByTeacherId(@PathVariable Integer teacherId){
        if(teacherId==null){
            return CommonResult.failed("教师ID不能为空");
        }
        return CommonResult.success(courseNoticeService.getCourseNoticeByTeacherId(teacherId));
    }

    @ApiOperation("添加课程公告")
    @RequestMapping(value = "",method = RequestMethod.POST)
    public CommonResult insertInfo(@RequestBody CourseNotice courseNotice){
        if(courseNotice.getCourseId()==null){
            return CommonResult.failed("课程Id不能为空");
        }else if(courseNotice.getTeacherId()==null){
            return CommonResult.failed("创建人Id不能为空");
        }
        else if(courseNoticeService.insertCourseNoticeInfo(courseNotice)>0){
            return CommonResult.success();
        }
        return CommonResult.failed();
    }
    @ApiOperation("删除课程公告")
    @RequestMapping(value="/{courseNoticeId}",method = RequestMethod.DELETE)
    public CommonResult deleteInfo(@PathVariable List<Integer> courseNoticeId){
        if(courseNoticeId==null){
            return CommonResult.failed("课程公告Id不能为空");
        }
        else if(courseNoticeService.deleteCourseNoticeInfo(courseNoticeId)>0) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }
    @ApiOperation("更新课程公告")
    @RequestMapping(value = "",method = RequestMethod.PUT)
    public CommonResult updateInfo(@RequestBody CourseNotice courseNotice){
        log.info(courseNotice.toString());
        if(courseNotice.getId()==null){
            return CommonResult.failed("课程公告Id不能为空");
        }
        else if(courseNotice.getTeacherId()==null) {
            return CommonResult.failed("课程公告修改Id不能为空");
        }
        if(courseNoticeService.updateCourseNoticeInfo(courseNotice)>0){
            return CommonResult.success();
        }
        return CommonResult.failed();
    }
}
