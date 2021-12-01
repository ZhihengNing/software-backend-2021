package com.yuki.experiment.framework.controller;

import com.yuki.experiment.common.result.CommonResult;
import com.yuki.experiment.framework.entity.Experiment;
import com.yuki.experiment.framework.entity.ExperimentFile;
import com.yuki.experiment.framework.entity.StuExperiment;
import com.yuki.experiment.framework.service.ExperimentFileService;
import com.yuki.experiment.framework.service.ExperimentService;
import com.yuki.experiment.framework.service.StuExperimentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    @RequestMapping(value = "/courseId/{courseId}",method = RequestMethod.GET )
    public CommonResult<List<Experiment>>getExperimentByCourseId(@PathVariable Integer courseId) {
        if (courseId == null) {
            return CommonResult.failed("课程Id不能为空");
        }
        List<Experiment> byCourseId = experimentService.getExperiment(courseId,null);
        return CommonResult.success(byCourseId);
    }

    @ApiOperation("查看实验项目")
    @RequestMapping(value = "/teacherId/{teacherId}",method = RequestMethod.GET )
    public CommonResult<List<Experiment>> getExperimentByTeacherId(@PathVariable Integer teacherId) {
        if (teacherId == null) {
            return CommonResult.failed("教师Id不能为空");
        }
        List<Experiment> byTeacherId = experimentService.getExperiment(null,teacherId);
        return CommonResult.success(byTeacherId);
    }

    @ApiOperation("插入实验项目")
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
    @RequestMapping(value = "/experimentId/{experimentIds}",method = RequestMethod.DELETE)
    public CommonResult deleteExperiment(@PathVariable List<Integer> experimentIds) {
        if (experimentIds == null) {
            return CommonResult.failed("实验项目Id不能为空");
        } else if (experimentService.delete(experimentIds) > 0) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取实验项目资料by实验Id")
    @RequestMapping(value = "/file/experimentId/{experimentId}",method = RequestMethod.GET)
    public CommonResult<List<ExperimentFile>> getInfoByExperimentId(@PathVariable Integer experimentId){
        if(experimentId==null){
            return CommonResult.failed("实验项目Id不能为空");
        }
        return CommonResult.success(experimentFileService.getInfo(experimentId,null));
    }

    @ApiOperation("获取实验项目资料by项目Id")
    @RequestMapping(value = "/file/teacherId/{teacherId}",method = RequestMethod.GET)
    public CommonResult<List<ExperimentFile>> getInfoByTeacherId(@PathVariable Integer teacherId){
        if(teacherId==null){
            return CommonResult.failed("教师Id不能为空");
        }
        return CommonResult.success(experimentFileService.getInfo(null,teacherId));
    }

    @ApiOperation("上传实验项目资料")
    @RequestMapping(value = "/file/{experimentId}/{teacherId}",method = RequestMethod.POST)
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

    @ApiOperation("查看学生实验作业by学生Id,实验Id")
    @RequestMapping(value = "/work/{experimentId}/{studentId}", method = RequestMethod.GET)
    public CommonResult<List<StuExperiment>> getStuExperiment(@PathVariable Integer studentId,
                                                              @PathVariable Integer experimentId) {
        if (studentId == null) {
            return CommonResult.failed("学生Id不能为空");
        } else if (experimentId == null) {
            return CommonResult.failed("实验项目Id不能为空");
        }
        return CommonResult.success(stuExperimentService.getStuExperiment(studentId, experimentId));
    }

    @ApiOperation("查看学生实验作业by学生Id")
    @RequestMapping(value = "/work/studentId/{studentId}", method = RequestMethod.GET)
    public CommonResult<List<StuExperiment>> getStuExperimentByStudentId(@PathVariable Integer studentId) {
        if(studentId==null){
            return CommonResult.failed("学生Id不能为空");
        }
        return CommonResult.success(stuExperimentService.getStuExperiment(studentId,null));
    }

    @ApiOperation("查看学生实验作业by实验Id")
    @RequestMapping(value = "/work/experimentId/{experimentId}",method = RequestMethod.GET)
    public CommonResult<List<StuExperiment>>getStuExperimentByExperimentId(@PathVariable Integer experimentId){
        if(experimentId==null){
            return CommonResult.failed("实验项目Id不能为空");
        }
        return CommonResult.success(stuExperimentService.getStuExperiment(null,experimentId));
    }

    @ApiOperation("学生提交实验作业")
    @RequestMapping(value = "/work/{experimentId}/{studentId}", method = RequestMethod.POST)
    public CommonResult insetStuExperiment(@RequestPart(value = "file",required = false) MultipartFile multipartFile,
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
    @RequestMapping(value = "/work/{experimentId}/{studentId}",method = RequestMethod.PUT)
    public CommonResult updateStuExperiment(@RequestPart(value = "file",required = false) MultipartFile multipartFile,
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


}
