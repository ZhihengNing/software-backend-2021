package com.yuki.experiment.framework.controller;

import com.yuki.experiment.common.result.CommonResult;
import com.yuki.experiment.framework.entity.Practice;
import com.yuki.experiment.framework.service.PracticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "对抗练习模块")
@RequestMapping("/practice")
public class PracticeController {

    @Resource
    private PracticeService practiceService;

    @ApiOperation("上传对抗练习")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public CommonResult<Practice> addPractice(@RequestBody Practice practice) {
        Practice insert = practiceService.insert(practice);
        if (insert != null) {
            return CommonResult.success(practice);
        }
        return CommonResult.failed();
    }

    @ApiOperation("查看对抗练习")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public CommonResult<List<Practice>> queryPractice(
            @RequestParam(value = "courseId",required = false)Integer courseId,
            @RequestParam(value = "teacherId",required = false)Integer teacherId,
            @RequestParam(value = "practiceId",required = false)Integer practiceId) {
        return CommonResult.success(practiceService.getAll(courseId, teacherId, practiceId));

    }
}
