package com.yuki.experiment.framework.controller;

import com.yuki.experiment.common.result.CommonResult;
import com.yuki.experiment.framework.entity.ExperimentFile;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/experimentFile")
public class ExperimentFileController {
    @ApiOperation("获取实验项目资料")
    @RequestMapping(value = "/{experimentId}",method = RequestMethod.GET)
    public CommonResult<List<ExperimentFile>> getInfoByExperimentId(@PathVariable Integer experimentId){


    }


}
