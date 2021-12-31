package com.yuki.experiment.framework.controller;

import com.yuki.experiment.common.result.CommonResult;
import com.yuki.experiment.framework.entity.Practice;
import com.yuki.experiment.framework.service.PracticeService;
import com.yuki.experiment.framework.service.impl.PracticeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/practice")
public class PracticeController {

    @Autowired
    private PracticeService practiceServiceImpl;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public CommonResult<List<Practice>> insert(@RequestBody Practice practice) {
        practiceServiceImpl.insert(practice);
        return CommonResult.success();

    }

    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public CommonResult<List<Practice>> get() {
        return CommonResult.success(practiceServiceImpl.getAll());

    }

}
