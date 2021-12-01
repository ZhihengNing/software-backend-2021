package com.yuki.experiment.framework.controller;

import com.yuki.experiment.common.result.CommonResult;
import com.yuki.experiment.framework.service.StuExperimentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/grade")
@Api("成绩模块")
@Slf4j
public class GradeController {

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
}
