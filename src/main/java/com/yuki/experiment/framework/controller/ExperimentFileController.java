package com.yuki.experiment.framework.controller;

import com.yuki.experiment.common.result.CommonResult;
import com.yuki.experiment.framework.entity.ExperimentFile;
import com.yuki.experiment.framework.service.ExperimentFileService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/experimentFile")
public class ExperimentFileController {

    private ExperimentFileService experimentFileService;
    @Autowired
    public void setExperimentFileService(ExperimentFileService experimentFileService) {
        this.experimentFileService = experimentFileService;
    }

    @ApiOperation("获取实验项目资料")
    @RequestMapping(value = "/experiment/{experimentId}",method = RequestMethod.GET)
    public CommonResult<List<ExperimentFile>> getInfoByExperimentId(@PathVariable Integer experimentId){
        if(experimentId==null){
            return CommonResult.failed("实验项目Id不能为空");
        }
        return CommonResult.success(experimentFileService.getInfo(experimentId,null));
    }

    @ApiOperation("获取实验项目资料")
    @RequestMapping(value = "/teacher/{teacherId}",method = RequestMethod.GET)
    public CommonResult<List<ExperimentFile>> getInfoByTeacherId(@PathVariable Integer teacherId){
        if(teacherId==null){
            return CommonResult.failed("教师Id不能为空");
        }
        return CommonResult.success(experimentFileService.getInfo(null,teacherId));
    }

    @ApiOperation("上传实验项目资料")
    @RequestMapping(value = "/{experimentId}/{teacherId}",method = RequestMethod.POST)
    public CommonResult insertExperimentFile(@RequestPart List<MultipartFile> multipartFiles,
                                             @PathVariable Integer experimentId,
                                             @PathVariable Integer teacherId) {
        if (multipartFiles == null) {
            return CommonResult.failed("实验项目资料不能为空");
        } else if (experimentId == null) {
            return CommonResult.failed("实验项目Id不能为空");
        } else if (teacherId == null) {
            return CommonResult.failed("教师Id不能为空");
        } else if (experimentFileService.insert(multipartFiles, experimentId, teacherId) > 0) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }


}
