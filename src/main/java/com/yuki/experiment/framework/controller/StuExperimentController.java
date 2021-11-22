package com.yuki.experiment.framework.controller;

import com.yuki.experiment.common.result.CommonResult;
import com.yuki.experiment.common.utils.FileUtil;
import com.yuki.experiment.framework.entity.StuExperiment;
import com.yuki.experiment.framework.service.StuExperimentService;
import io.swagger.annotations.ApiOperation;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Member;
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
    @RequestMapping("/experiment/{experimentId}")
    public CommonResult<List<StuExperiment>>getStuExperimentByExperimentId(@PathVariable Integer experimentId){
        if(experimentId==null){
            return CommonResult.failed("实验项目Id不能为空");
        }
        return CommonResult.success(stuExperimentService.getStuExperimentByExperimentId(experimentId));
    }

    @RequestMapping(value = "/{studentId}/{experimentId}", method = RequestMethod.POST)
    public CommonResult insetStuExperiment(@RequestPart MultipartFile multipartFile,
                                           @PathVariable("studentId") Integer studentId,
                                           @PathVariable("experimentId") Integer experimentId,
                                           @RequestParam("jobContent") String jobContent) {
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
}
