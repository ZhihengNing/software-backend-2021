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

import java.util.Date;
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

    @ApiOperation("获取所有系统公告")
    @RequestMapping(value = "/system",method = RequestMethod.GET)
    public CommonResult<List<Notice>> getSysNotice() {
        return CommonResult.success(noticeService.getAllNotice());
    }

    @ApiOperation("获取系统公告by公告Id")
    @RequestMapping(value = "/system/noticeId/{noticeId}",method = RequestMethod.GET)
    public CommonResult<Notice>getSysNotice(@PathVariable Integer noticeId) {
        if (noticeId == null) {
            return CommonResult.failed("公告Id不能为空");
        }
        return CommonResult.success(noticeService.getNoticeById(noticeId));
    }

    @ApiOperation("模糊查询系统公告")
    @RequestMapping(value = "/system/fuzzyQuery",method = RequestMethod.GET)
    public CommonResult<List<Notice>> fuzzyQuerySysNotice(@RequestParam("keyword")String keyword){
        return CommonResult.success(noticeService.fuzzyQuery(keyword));
    }

    @ApiOperation("时间段查询系统公告")
    @RequestMapping(value = "/system/getByTime",method = RequestMethod.GET)
    public CommonResult<List<Notice>> querySysByTime(@RequestParam("beginDate")Date beginDate,
                                                  @RequestParam(value = "endDate",required = false)Date endDate) {
        endDate = endDate == null ? beginDate : endDate;
        if (beginDate == null) {
            return CommonResult.failed("开始时间不能为空");
        } else if (endDate.compareTo(beginDate) < 0) {
            return CommonResult.failed("结束时间不能小于开始时间");
        }
        return CommonResult.success(noticeService.queryByTime(beginDate, endDate));
    }


    @ApiOperation("添加系统公告")
    @RequestMapping(value = "/system",method = RequestMethod.POST)
    public CommonResult<Notice> insertSysNotice(@RequestBody Notice notice){
        if(notice.getAdministratorId()==null){
            return CommonResult.failed("公告创建人Id不能为空");
        }
        else if(noticeService.insertNotice(notice)>0){
            return CommonResult.success(notice);
        }
        return CommonResult.failed();
    }

    @ApiOperation("更新系统公告")
    @RequestMapping(value = "/system",method = RequestMethod.PUT)
    public CommonResult<Notice> updateSysNotice(@RequestBody Notice notice) {
        if (notice.getId() == null) {
            return CommonResult.failed("公告Id不能为空");
        } else if (notice.getAdministratorId() == null) {
            return CommonResult.failed("公告修改人Id不能为空");
        }
        Notice notice1 = noticeService.updateNotice(notice);
        if (notice1 != null) {
            return CommonResult.success(notice1);
        }
        return CommonResult.failed();
    }

    @ApiOperation("删除系统公告")
    @RequestMapping(value = "/system/noticeId/{noticeId}",method = RequestMethod.DELETE)
    public CommonResult deleteSysNotice(@PathVariable List<Integer> noticeId) {
        if (noticeService.deleteNotice(noticeId) > 0) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @ApiOperation("查询课程公告by课程Id")
    @RequestMapping(value = "/course/courseId/{courseId}",method = RequestMethod.GET)
    public CommonResult<List<CourseNotice>>getCourseNoticeByCourseId(@PathVariable("courseId") Integer courseId){
        if(courseId==null){
            return CommonResult.failed("课程ID不能为空");
        }
        return CommonResult.success(courseNoticeService.getCourseNotice(courseId,null));
    }

    @ApiOperation("查询课程公告by老师Id")
    @RequestMapping(value = "/course/teacherId/{teacherId}",method = RequestMethod.GET)
    public CommonResult<List<CourseNotice>>getCourseNoticeByTeacherId(@PathVariable("teacherId") Integer teacherId){
        if(teacherId==null){
            return CommonResult.failed("教师ID不能为空");
        }
        return CommonResult.success(courseNoticeService.getCourseNotice(null,teacherId));
    }

    @ApiOperation("模糊查询课程公告")
    @RequestMapping(value = "/course/fuzzyQuery",method = RequestMethod.GET)
    public CommonResult<List<CourseNotice>> fuzzyQueryCourseNotice(@RequestParam("keyword") String keyword) {
        return CommonResult.success(courseNoticeService.fuzzyQueryCourseNoticeInfo(keyword));
    }

    @ApiOperation("时间段查询课程公告")
    @RequestMapping(value = "/course/getByTime",method = RequestMethod.GET)
    public CommonResult<List<CourseNotice>> queryByTime(@RequestParam("beginDate")Date beginDate,
                                                        @RequestParam(value = "endDate",required = false) Date endDate) {
        endDate=endDate==null?beginDate:endDate;
        if (beginDate == null) {
            return CommonResult.failed("开始时间不能为空");
        } else if (endDate.compareTo(beginDate) < 0) {
            return CommonResult.failed("结束时间不能小于开始时间");
        }
        return CommonResult.success(courseNoticeService.queryByTime(beginDate, endDate));
    }

    @ApiOperation("添加课程公告")
    @RequestMapping(value = "/course",method = RequestMethod.POST)
    public CommonResult<CourseNotice> insertInfo(@RequestBody CourseNotice courseNotice){
        if(courseNotice.getCourseId()==null){
            return CommonResult.failed("课程Id不能为空");
        }else if(courseNotice.getTeacherId()==null){
            return CommonResult.failed("创建人Id不能为空");
        }
        else if(courseNoticeService.insertCourseNoticeInfo(courseNotice)>0){
            return CommonResult.success(courseNotice);
        }
        return CommonResult.failed();
    }

    @ApiOperation("删除课程公告")
    @RequestMapping(value="/course/courseNoticeId/{courseNoticeId}",method = RequestMethod.DELETE)
    public CommonResult deleteInfo(@PathVariable("courseNoticeId") List<Integer> courseNoticeId){
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
    public CommonResult<CourseNotice> updateInfo(@RequestBody CourseNotice courseNotice) {
        if (courseNotice.getId() == null) {
            return CommonResult.failed("课程公告Id不能为空");
        } else if (courseNotice.getTeacherId() == null) {
            return CommonResult.failed("课程公告修改人(教师Id)不能为空");
        }
        CourseNotice courseNotice1 = courseNoticeService.updateCourseNoticeInfo(courseNotice);
        if (courseNotice1 != null) {
            return CommonResult.success(courseNotice1);
        }
        return CommonResult.failed();
    }

}
