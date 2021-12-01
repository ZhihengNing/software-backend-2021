package com.yuki.experiment.framework.controller;

import com.yuki.experiment.common.result.CommonResult;
import com.yuki.experiment.framework.entity.CourseNotice;
import com.yuki.experiment.framework.entity.Notice;
import com.yuki.experiment.framework.service.CourseNoticeService;
import com.yuki.experiment.framework.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
@Api(tags = "公告模块")
@Slf4j
public class NoticeController {

    private NoticeService noticeService;

    private CourseNoticeService courseNoticeService;

    @Autowired
    public void setNoticeService(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Autowired
    public void setCourseNoticeService(CourseNoticeService courseNoticeService) {
        this.courseNoticeService = courseNoticeService;
    }

    @ApiOperation("获取系统公告")
    @RequestMapping(value = "/system",method = RequestMethod.GET)
    public CommonResult<List<Notice>> getNoticeInfo() {
        return CommonResult.success(noticeService.getAllNotice());
    }

    @ApiOperation("添加系统公告")
    @RequestMapping(value = "/system",method = RequestMethod.POST)
    public CommonResult insertNotice(@RequestBody Notice notice){
        if(notice.getAdministratorId()==null){
            return CommonResult.failed("公告创建人Id不能为空");
        }
        else if(noticeService.insertNotice(notice)>0){
            return CommonResult.success();
        }
        return CommonResult.failed();
    }
    @ApiOperation("更新系统公告")
    @RequestMapping(value = "/system",method = RequestMethod.PUT)
    public CommonResult updateNotice(@RequestBody Notice notice){
        if(notice.getId()==null){
            return CommonResult.failed("公告Id不能为空");
        }
        else if(notice.getAdministratorId()==null){
            return CommonResult.failed("公告修改人Id不能为空");
        }
        else if(noticeService.updateNotice(notice)>0){
            return CommonResult.success();
        }
        return CommonResult.failed();
    }
    @ApiOperation("删除系统公告")
    @RequestMapping(value = "/system/noticeId/{noticeId}",method = RequestMethod.DELETE)
    public CommonResult deleteNotice(@PathVariable List<Integer> noticeId) {
        if (noticeService.deleteNotice(noticeId) > 0) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @ApiOperation("查看课程公告")
    @RequestMapping(value = "/course/courseId/{courseId}",method = RequestMethod.GET)
    public CommonResult<List<CourseNotice>>getInfoByCourseId(@PathVariable("courseId") Integer courseId){
        if(courseId==null){
            return CommonResult.failed("课程ID不能为空");
        }
        return CommonResult.success(courseNoticeService.getCourseNotice(courseId,null));
    }
    @ApiOperation("查看课程公告")
    @RequestMapping(value = "/course/teacherId/{teacherId}",method = RequestMethod.GET)
    public CommonResult<List<CourseNotice>>getInfoByTeacherId(@PathVariable Integer teacherId){
        if(teacherId==null){
            return CommonResult.failed("教师ID不能为空");
        }
        return CommonResult.success(courseNoticeService.getCourseNotice(null,teacherId));
    }

    @ApiOperation("添加课程公告")
    @RequestMapping(value = "/course",method = RequestMethod.POST)
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
    @RequestMapping(value="/course/courseNoticeId/{courseNoticeId}",method = RequestMethod.DELETE)
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
    @RequestMapping(value = "/course",method = RequestMethod.PUT)
    public CommonResult updateInfo(@RequestBody CourseNotice courseNotice){
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
