package com.yuki.experiment.framework.controller;

import com.yuki.experiment.common.result.CommonResult;
import com.yuki.experiment.framework.entity.Student;
import com.yuki.experiment.framework.service.StudentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @ApiOperation("查看学生信息")
    @RequestMapping(value = "/{studentId}",method = RequestMethod.GET)
    public CommonResult<Student> getInfo( @PathVariable Integer studentId) {
        if (studentId == null) {
            return CommonResult.failed("学生Id不能为空");
        }
        return CommonResult.success(studentService.getInfo(studentId));
    }
}
