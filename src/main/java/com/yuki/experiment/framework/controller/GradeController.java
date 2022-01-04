package com.yuki.experiment.framework.controller;

import com.yuki.experiment.common.result.CommonResult;
import com.yuki.experiment.framework.dto.StudentGradeDTO;
import com.yuki.experiment.framework.service.CourseScoreService;
import com.yuki.experiment.framework.service.StuExperimentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/grade")
@Api(tags = "成绩模块")
@Slf4j
public class GradeController {

    private StuExperimentService stuExperimentService;

    private CourseScoreService courseScoreService;

    @Autowired
    public void setStuExperimentService(StuExperimentService stuExperimentService) {
        this.stuExperimentService = stuExperimentService;
    }

    @Autowired
    public void setCourseScoreService(CourseScoreService courseScoreService) {
        this.courseScoreService = courseScoreService;
    }

    @ApiOperation("上传学生实验项目成绩")
    @RequestMapping(value = "/{studentId}/{experimentId}", method = RequestMethod.POST)
    public CommonResult uploadStudentGrade(@PathVariable Integer studentId,
                                           @PathVariable Integer experimentId,
                                           @RequestParam("grade") BigDecimal grade) {
        if (studentId == null) {
            return CommonResult.failed("学生Id不能为空");
        } else if (experimentId == null) {
            return CommonResult.failed("实验项目Id不能为空");
        } else if (grade == null) {
            return CommonResult.failed("学生成绩不能为空");
        } else if (stuExperimentService.uploadGrade(studentId, experimentId, grade) > 0) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取学生某课程所有成绩(分为总成绩，实验，考勤，对抗练习）只返回已经激活学生")
    @RequestMapping(value = "/get/{studentId}/{courseId}", method = RequestMethod.GET)
    public CommonResult<StudentGradeDTO> getGrade(@PathVariable Integer studentId, @PathVariable Integer courseId) {
        if (studentId == null) {
            return CommonResult.failed("学生Id不能为空");
        }
        else if(courseId==null){
            return CommonResult.failed("课程Id不能为空");
        }
        StudentGradeDTO studentGrade = courseScoreService.getStudentGrade(studentId, courseId);
        if (studentGrade != null) {
            return CommonResult.success(studentGrade);
        }
        return CommonResult.failed("没有选这门课，查什么查");
    }

}
