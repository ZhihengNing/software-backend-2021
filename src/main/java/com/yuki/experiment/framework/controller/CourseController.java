package com.yuki.experiment.framework.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuki.experiment.common.exception.FileIsNullException;
import com.yuki.experiment.common.result.CommonResult;
import com.yuki.experiment.common.utils.FileUtil;
import com.yuki.experiment.framework.entity.Course;
import com.yuki.experiment.framework.entity.CourseFeedback;
import com.yuki.experiment.framework.entity.CourseFile;
import com.yuki.experiment.framework.entity.Teacher;
import com.yuki.experiment.framework.mapper.TeacherMapper;
import com.yuki.experiment.framework.service.CourseFeedbackService;
import com.yuki.experiment.framework.service.CourseFileService;
import com.yuki.experiment.framework.service.CourseScoreService;
import com.yuki.experiment.framework.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/course")
@Api(tags ="课程模块")
@Slf4j
public class CourseController {

    private CourseService courseService;

    private CourseFeedbackService courseFeedbackService;

    private CourseFileService courseFileService;

    private CourseScoreService courseScoreService;

    private final static String courseFileUploadPath = "course";

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @Autowired
    public void setCourseFeedbackService(CourseFeedbackService courseFeedbackService) {
        this.courseFeedbackService = courseFeedbackService;
    }

    @Autowired
    public void setCourseFileService(CourseFileService courseFileService) {
        this.courseFileService = courseFileService;
    }

    @Autowired
    public void setCourseScoreService(CourseScoreService courseScoreService) {
        this.courseScoreService = courseScoreService;
    }

    @Autowired
    private TeacherMapper mapper;

    @ApiOperation("这个不要用")
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

    @ApiOperation("查看课程反馈")
    @RequestMapping(value = "/feedback/courseId/{courseId}",method = RequestMethod.GET)
    public CommonResult<List<CourseFeedback>> getFeedbackByCourse(@PathVariable Integer courseId){
        List<CourseFeedback> feedbackByCourseId = courseFeedbackService.getFeedback(null,courseId);
        return CommonResult.success(feedbackByCourseId);
    }

    @ApiOperation("查看课程反馈")
    @RequestMapping(value = "/feedback/studentId/{studentId}",method = RequestMethod.GET)
    public CommonResult<List<CourseFeedback>> getFeedbackByStudent(@PathVariable Integer studentId){
        List<CourseFeedback> feedbackByStudentId = courseFeedbackService.getFeedback(studentId,null);
        return CommonResult.success(feedbackByStudentId);
    }

    @ApiOperation("插入课程反馈")
    @RequestMapping(value = "/feedback",method =RequestMethod.POST)
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
    @RequestMapping(value = "/feedback",method = RequestMethod.PUT)
    public CommonResult updateFeedback(@RequestBody CourseFeedback courseFeedback) {
        if (courseFeedback.getId() == null) {
            return CommonResult.failed("课程反馈Id不能为空");
        } else if (courseFeedbackService.updateFeedback(courseFeedback) > 0) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @ApiOperation("删除课程反馈")
    @RequestMapping(value = "/feedback/{feedbackId}",method = RequestMethod.DELETE)
    public CommonResult deleteFeedback(@PathVariable List<Integer> feedbackId){
        if(feedbackId==null){
            return CommonResult.failed("课程反馈Id不能为空");
        }
        else if(courseFeedbackService.deleteFeedback(feedbackId)>0) {
            return CommonResult.success();
        }
        return CommonResult.failed();

    }

    @ApiOperation("上传课程文件")
    @RequestMapping(value = "/file/{courseId}/{teacherId}", method = RequestMethod.POST)
    @Transactional(rollbackFor= FileIsNullException.class)
    public CommonResult uploadCourseFile(@RequestPart("courseFile") List<MultipartFile> multipartFiles,
                                         @PathVariable("courseId") Integer courseId,
                                         @PathVariable("teacherId") Integer teacherId) {
        //生成文件夹的路径
        String path = FileUtil.generatorUrl(courseFileUploadPath, courseId);

        String webPath=FileUtil.generatorWebUrl(courseFileUploadPath,courseId);
        //保存到服务器
        List<JSONObject> list = FileUtil.preserveFile(multipartFiles, path,webPath);
        for (JSONObject item : list) {
            String url = item.getString("data");
            String name = item.getString("name");
            //保存到数据库
            if (url != null&&courseFileService.insertFile(name, courseId, teacherId, url)>0) {
                log.info(name+"插入数据库成功");
            }
        }
        return CommonResult.success();
    }

    @ApiOperation("查看课程文件")
    @RequestMapping(value = "/file/courseId/{courseId}",method = RequestMethod.GET)
    public CommonResult<List<CourseFile>> getFileListByCourseId(@PathVariable Integer courseId){
        if(courseId==null){
            return CommonResult.failed("课程Id不能为空");
        }
        List<CourseFile> courseFileByCourseId = courseFileService.getCourseFileByCourseId(courseId);
        return CommonResult.success(courseFileByCourseId);
    }

    @ApiOperation("查看课程文件")
    @RequestMapping(value = "/file/teacherId/{teacherId}",method = RequestMethod.GET)
    public CommonResult<List<CourseFile>> getFileListByTeacherId(@PathVariable Integer teacherId){
        if(teacherId==null){
            return CommonResult.failed("教师Id不能为空");
        }
        List<CourseFile> courseFileByTeacherId = courseFileService.getCourseFileByTeacherId(teacherId);
        return CommonResult.success(courseFileByTeacherId);
    }

    @ApiOperation("删除课程文件")
    @RequestMapping(value = "/file/{fileIds}",method = RequestMethod.DELETE)
    public CommonResult deleteFile(@PathVariable List<Integer> fileIds) {
        if(courseFileService.deleteFile(fileIds)>0){
            return CommonResult.success();
        }
        return CommonResult.failed();
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
