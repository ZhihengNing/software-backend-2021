package com.yuki.experiment.framework.controller;

import com.yuki.experiment.common.result.CommonResult;
import com.yuki.experiment.framework.entity.Practice;
import com.yuki.experiment.framework.service.PracticeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/practice")
public class PracticeController {

    @Resource
    private PracticeService practiceService;

    @ApiOperation("老师上传对抗练习")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonResult<Practice> addPractice(@RequestBody Practice practice) {
        Practice insert = practiceService.insert(practice);
        if (insert != null) {
            return CommonResult.success(practice);
        }
        return CommonResult.failed();
    }

    @ApiOperation("老师查看对抗练习")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public CommonResult<List<Practice>> queryPractice(@RequestParam(value = "courseId",required = false)
                                                                  Integer courseId) {
        return CommonResult.success(practiceService.getAllByCourseId(courseId));

    }
}
