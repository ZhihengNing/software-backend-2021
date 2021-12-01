package com.yuki.experiment.framework.controller;

import com.yuki.experiment.common.result.CommonResult;
import com.yuki.experiment.common.utils.JsonUtil;
import com.yuki.experiment.framework.service.StuExperimentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/grade")
@Api(tags = "成绩模块")
@Slf4j
public class GradeController {

    private static final String scoreRatioPath="src/main/resources/scoreRatio.json";

    private StuExperimentService stuExperimentService;

    @Autowired
    public void setStuExperimentService(StuExperimentService stuExperimentService) {
        this.stuExperimentService = stuExperimentService;
    }

    @ApiOperation("上传学生实验项目成绩")
    @RequestMapping(value = "/{studentId}/{experimentId}",method = RequestMethod.POST)
    public CommonResult uploadStudentGrade(@PathVariable Integer studentId,
                                           @PathVariable Integer experimentId,
                                           @RequestParam("grade") BigDecimal grade){
        if (studentId == null) {
            return CommonResult.failed("学生Id不能为空");
        } else if (experimentId == null) {
            return CommonResult.failed("实验项目Id不能为空");
        }
        else if(grade==null){
            return CommonResult.failed("学生成绩不能为空");
        }else if(stuExperimentService.uploadGrade(studentId,experimentId,grade)>0){
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取学生某课程所有实验成绩")
    @RequestMapping(value = "/get/{studentId}/{courseId}",method = RequestMethod.GET)
    public CommonResult<List<BigDecimal>> getGrade(@PathVariable Integer studentId, @PathVariable Integer courseId) {
        if (studentId == null) {
            return CommonResult.failed("学生Id不能为空");
        }
        if (JsonUtil.readJson(scoreRatioPath) == null) {
            return CommonResult.failed("评分标准都没给，怎么算分，真是可恶！！！");
        }
        return CommonResult.success(stuExperimentService.getStudentGrade(studentId, courseId));
    }
}
