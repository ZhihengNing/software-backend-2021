package com.yuki.experiment.framework.controller;

import com.yuki.experiment.common.result.CommonResult;
import com.yuki.experiment.framework.entity.Experiment;
import com.yuki.experiment.framework.service.ExperimentService;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/experiment")
public class ExperimentController {
    private ExperimentService experimentService;
    @Autowired
    public void setExperimentService(ExperimentService experimentService) {
        this.experimentService = experimentService;
    }

    @ApiOperation("查看实验项目")
    @RequestMapping(value = "/course/{courseId}",method = RequestMethod.GET )
    public CommonResult<List<Experiment>>getExperimentByCourseId(@PathVariable Integer courseId) {
        if (courseId == null) {
            return CommonResult.failed("课程Id不能为空");
        }
        List<Experiment> byCourseId = experimentService.getByCourseId(courseId);
        return CommonResult.success(byCourseId);
    }

    @ApiOperation("查看实验项目")
    @RequestMapping(value = "/teacher/{teacherId}",method = RequestMethod.GET )
    public CommonResult<List<Experiment>> getExperimentByTeacherId(@PathVariable Integer teacherId) {
        if (teacherId == null) {
            return CommonResult.failed("教师Id不能为空");
        }
        List<Experiment> byTeacherId = experimentService.getByTeacherId(teacherId);
        return CommonResult.success(byTeacherId);
    }

    @ApiModelProperty("插入实验项目")
    @RequestMapping(value = "",method = RequestMethod.POST)
    public CommonResult insertExperiment(@RequestBody Experiment experiment){
        if(experiment.getCourseId()==null){
            return CommonResult.failed("课程Id不能为空");
        }
        else if(experimentService.insert(experiment)>0){
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @ApiOperation("更新实验项目")
    @RequestMapping(value = "",method = RequestMethod.PUT)
    public CommonResult updateExperiment(@RequestBody Experiment experiment){
        if(experiment.getId()==null){
            return CommonResult.failed("实验项目Id不能为空");
        }
        else if(experimentService.update(experiment)>0){
            return CommonResult.success();
        }
        return CommonResult.failed();
    }
    @ApiOperation("删除实验项目")
    @RequestMapping(value = "/{experimentIds}",method = RequestMethod.DELETE)
    public CommonResult deleteExperiment(@PathVariable List<Integer> experimentIds) {
        if (experimentIds == null) {
            return CommonResult.failed("实验项目Id不能为空");
        } else if (experimentService.delete(experimentIds) > 0) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }


}
