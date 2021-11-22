package com.yuki.experiment.framework.controller;

import com.oracle.tools.packager.Log;
import com.yuki.experiment.common.result.CommonResult;
import com.yuki.experiment.framework.entity.StuExperiment;
import com.yuki.experiment.framework.service.StuExperimentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/studentExperiment")
public class StuExperimentController {

    private StuExperimentService stuExperimentService;

    @Autowired
    public void setStuExperimentService(StuExperimentService stuExperimentService) {
        this.stuExperimentService = stuExperimentService;
    }

    @ApiOperation("查看实验文件")
    @RequestMapping(value = "/{studentId}/{experimentId}", method = RequestMethod.GET)
    public CommonResult<StuExperiment> getStuExperiment(@PathVariable Integer studentId,
                                                        @PathVariable Integer experimentId) {
        if (studentId == null) {
            return CommonResult.failed("学生Id不能为空");
        } else if (experimentId == null) {
            return CommonResult.failed("实验项目Id不能为空");
        }
        return CommonResult.success(stuExperimentService.getStuExperiment(studentId, experimentId));
    }

    @ApiOperation("查看实验文件")
    @RequestMapping(value = "/student/{studentId}", method = RequestMethod.GET)
    public CommonResult<List<StuExperiment>> getStuExperimentByStudentId(@PathVariable Integer studentId) {
        if(studentId==null){
            return CommonResult.failed("学生Id不能为空");
        }
        return CommonResult.success(stuExperimentService.getStuExperimentByStudentId(studentId));
    }

    @ApiOperation("查看实验文件")
    @RequestMapping(value = "/experiment/{experimentId}",method = RequestMethod.GET)
    public CommonResult<List<StuExperiment>>getStuExperimentByExperimentId(@PathVariable Integer experimentId){
        if(experimentId==null){
            return CommonResult.failed("实验项目Id不能为空");
        }
        return CommonResult.success(stuExperimentService.getStuExperimentByExperimentId(experimentId));
    }

    @ApiOperation("学生提交实验项目作业")
    @RequestMapping(value = "/{studentId}/{experimentId}", method = RequestMethod.POST)
    public CommonResult insetStuExperiment(@RequestPart("file") MultipartFile multipartFile,
                                           @PathVariable("studentId") Integer studentId,
                                           @PathVariable("experimentId") Integer experimentId,
                                           @RequestParam("jobContent") String jobContent) {
        Log.info((multipartFile==null?"1":"0"));
        if (studentId == null) {
            return CommonResult.failed("学生Id不能为空");
        } else if (experimentId == null) {
            return CommonResult.failed("实验项目Id不能为空");
        }
        StuExperiment build = StuExperiment.builder().studentId(studentId)
                .experimentId(experimentId)
                .jobContent(jobContent).build();
        if (stuExperimentService.insert(multipartFile, build) > 0) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @ApiOperation("学生更新实验作业")
    @RequestMapping(value = "/{studentId}/{experimentId}",method = RequestMethod.PUT)
    public CommonResult updateStuExperiment(@RequestPart("file") MultipartFile multipartFile,
                                            @PathVariable Integer studentId,
                                            @PathVariable Integer experimentId,
                                            @RequestParam("jobContent") String jobContent) {
        if (studentId == null) {
            return CommonResult.failed("学生Id不能为空");
        } else if (experimentId == null) {
            return CommonResult.failed("实验项目Id不能为空");
        }
        if (stuExperimentService.update(multipartFile, studentId, experimentId, jobContent) > 0) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @ApiOperation("上传学生实验项目成绩")
    @RequestMapping(value = "/grade/{studentId}/{experimentId}",method = RequestMethod.POST)
    public CommonResult uploadStudentGrade(@PathVariable Integer studentId,
                                           @PathVariable Integer experimentId,
                                           @RequestParam("grade")BigDecimal grade){
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
