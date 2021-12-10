package com.yuki.experiment.framework.controller;

import com.yuki.experiment.common.result.CommonResult;
import com.yuki.experiment.common.utils.FileUtil;
import com.yuki.experiment.framework.entity.Administrator;
import com.yuki.experiment.framework.mapper.CourseNoticeMapper;
import com.yuki.experiment.framework.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.util.List;

@RestController
@RequestMapping("/test")
@Api(tags= "测试模块(禁用)")
@Slf4j
public class TestController {

    private final TestService service;

    @Autowired
    private CourseNoticeMapper courseNoticeMapper;

    public TestController(TestService service){
        this.service=service;
    }
    @ApiOperation("这是一个测试controller")
    @RequestMapping(value = "/select",method = RequestMethod.GET)
    public CommonResult<List<Administrator>> demo(){
        //wrapper.select("name","create_time").ge("id",1951121);
        return CommonResult.success(service.selectList());
    }

    @ApiOperation("插入测试")
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public CommonResult<Integer> insert(@RequestBody Administrator administrator) {
        return CommonResult.success(service.insert(administrator));
    }

    @ApiOperation("删除测试")
    @RequestMapping(value="delete",method = RequestMethod.DELETE)
    public CommonResult<Integer>delete(@RequestParam("name") String name) {
        return CommonResult.success(service.delete(name));
    }

    @ApiOperation("更新测试")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public CommonResult<Integer> update(@RequestBody Administrator administrator) {
        return CommonResult.success(service.update(administrator));
    }

    @ApiOperation("这是一个测试controller")
    @RequestMapping(value = "/demo",method = RequestMethod.GET)
    public CommonResult<String> demo1() {
        return CommonResult.success("2333");
    }

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public CommonResult uploadFile(@RequestPart("file") List<MultipartFile>multipartFiles) {
        return CommonResult.success();
    }

}
