package com.yuki.experiment.framework.controller;

import com.yuki.experiment.common.result.CommonResult;
import com.yuki.experiment.common.utils.EmptyUtil;
import com.yuki.experiment.framework.entity.Experiment;
import com.yuki.experiment.framework.entity.ExperimentFile;
import com.yuki.experiment.framework.entity.StuExperiment;
import com.yuki.experiment.framework.service.ExperimentFileService;
import com.yuki.experiment.framework.service.ExperimentService;
import com.yuki.experiment.framework.service.StuExperimentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.expr.Expr;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/experiment")
@Api(tags ="实验模块")
@Slf4j
public class ExperimentController {

    private ExperimentService experimentService;

    private ExperimentFileService experimentFileService;

    private StuExperimentService stuExperimentService;

    @Autowired
    public void setExperimentService(ExperimentService experimentService) {
        this.experimentService = experimentService;
    }

    @Autowired
    public void setExperimentFileService(ExperimentFileService experimentFileService) {
        this.experimentFileService = experimentFileService;
    }

    @Autowired
    public void setStuExperimentService(StuExperimentService stuExperimentService) {
        this.stuExperimentService = stuExperimentService;
    }


    @ApiOperation("查看实验项目")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public CommonResult<List<Experiment>> queryExperiment(
            @RequestParam(value = "courseId", required = false) Integer courseId,
            @RequestParam(value = "teacherId", required = false) Integer teacherId,
            @RequestParam(value = "experimentId", required = false) Integer experimentId) {
        List<Experiment> byCourseId = experimentService
                .getExperiment(courseId, teacherId, experimentId);
        return CommonResult.success(byCourseId);
    }


    @ApiOperation("插入实验项目")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public CommonResult<Experiment> insertExperiment(@RequestBody Experiment experiment) {
        if (experiment.getCourseId() == null) {
            return CommonResult.failed("课程Id不能为空");
        }
        Experiment insert = experimentService.insert(experiment);
        if (insert != null) {
            return CommonResult.success(insert);
        }
        return CommonResult.failed();
    }

    @ApiOperation("更新实验项目")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public CommonResult<Experiment> updateExperiment(@RequestBody Experiment experiment) {
        if (experiment.getId() == null) {
            return CommonResult.failed("实验项目Id不能为空");
        }
        Experiment update = experimentService.update(experiment);
        if (update != null) {
            return CommonResult.success(update);
        }
        return CommonResult.failed();
    }

    @ApiOperation("删除实验项目")
    @RequestMapping(value = "/experimentId/{experimentIds}", method = RequestMethod.DELETE)
    public CommonResult deleteExperiment(@PathVariable List<Integer> experimentIds) {
        if (EmptyUtil.isEmpty(experimentIds)) {
            return CommonResult.failed("实验项目Id不能为空");
        } else if (experimentService.delete(experimentIds) > 0) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取实验项目资料")
    @RequestMapping(value = "/file", method = RequestMethod.GET)
    public CommonResult<List<ExperimentFile>> getInfoByExperimentId(
            @RequestParam(value = "experimentId", required = false) Integer experimentId,
            @RequestParam(value = "teacherId", required = false) Integer teacherId,
            @RequestParam(value = "experimentFileId", required = false) Integer experimentFileId) {
        return CommonResult.success(experimentFileService
                .getInfo(experimentId, teacherId, experimentFileId));
    }

    @ApiOperation("上传实验项目资料")
    @RequestMapping(value = "/file/{experimentId}/{teacherId}", method = RequestMethod.POST)
    public CommonResult<List<ExperimentFile>> insertExperimentFile(@RequestPart List<MultipartFile> multipartFiles,
                                             @PathVariable Integer experimentId,
                                             @PathVariable Integer teacherId) {
        if (EmptyUtil.isEmpty(multipartFiles)) {
            return CommonResult.failed("实验项目资料不能为空");
        } else if (experimentId == null) {
            return CommonResult.failed("实验项目Id不能为空");
        } else if (teacherId == null) {
            return CommonResult.failed("教师Id不能为空");
        }
        List<ExperimentFile> insert = experimentFileService.insert(multipartFiles, experimentId, teacherId);
        if (!EmptyUtil.isEmpty(insert)) {
            return CommonResult.success(insert);
        }
        return CommonResult.failed();
    }

    @ApiOperation("查看学生实验作业")
    @RequestMapping(value = "/work", method = RequestMethod.GET)
    public CommonResult<List<StuExperiment>> getStuExperiment(
            @RequestParam(value = "studentId", required = false) Integer studentId,
            @RequestParam(value = "experimentId", required = false) Integer experimentId) {
        return CommonResult.success(stuExperimentService.getStuExperiment(studentId, experimentId));
    }


    @ApiOperation("学生提交实验作业")
    @RequestMapping(value = "/work/{experimentId}/{studentId}", method = RequestMethod.POST)
    public CommonResult insetStuExperiment(@RequestPart(value = "file", required = false) MultipartFile multipartFile,
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

    @ApiOperation("学生更新实验作业")
    @RequestMapping(value = "/work/{experimentId}/{studentId}", method = RequestMethod.PUT)
    public CommonResult<StuExperiment> updateStuExperiment(@PathVariable Integer studentId,
                                                           @PathVariable Integer experimentId,
                                                           @RequestPart(value = "file", required = false) MultipartFile multipartFile,
                                                           @RequestParam(value = "jobContent", required = false) String jobContent) {
        if (studentId == null) {
            return CommonResult.failed("学生Id不能为空");
        } else if (experimentId == null) {
            return CommonResult.failed("实验项目Id不能为空");
        }
        StuExperiment update = stuExperimentService.update(multipartFile, studentId, experimentId, jobContent);
        if (update != null) {
            return CommonResult.success(update);
        }
        return CommonResult.failed();
    }

}
