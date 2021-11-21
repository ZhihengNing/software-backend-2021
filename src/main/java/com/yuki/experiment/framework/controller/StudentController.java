package com.yuki.experiment.framework.controller;

import com.yuki.experiment.common.result.CommonResult;
import com.yuki.experiment.framework.entity.Student;
import com.yuki.experiment.framework.service.StudentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private StudentService studentService;
    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @ApiOperation("查看学生信息")
    @RequestMapping(value = "/{studentId}",method = RequestMethod.GET)
    public CommonResult<Student> getInfo( @PathVariable Integer studentId) {
        if (studentId == null) {
            return CommonResult.failed("学生Id不能为空");
        }
        return CommonResult.success(studentService.getInfo(studentId));
    }
    @ApiOperation("插入学生信息")
    @RequestMapping(value = "",method = RequestMethod.POST)
    public CommonResult insertInfo(@RequestBody Student student) {
        if (student.getAdministratorId() == null) {
            return CommonResult.failed("管理员Id不能为空");
        } else if (student.getName() == null) {
            return CommonResult.failed("学生姓名不能为空");
        } else if (student.getPassword() == null) {
            return CommonResult.failed("密码不能为空");
        } else if (studentService.insertInfo(student) > 0) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }
    @ApiOperation("更新学生信息")
    @RequestMapping(value = "",method = RequestMethod.PUT)
    public CommonResult updateInfo(@RequestBody Student student) {
        if (student.getId() == null) {
            return CommonResult.failed("学生Id不能为空");
        } else if (student.getAdministratorId() == null) {
            return CommonResult.failed("管理员Id不能为空");
        } else if (student.getName() == null) {
            return CommonResult.failed("学生姓名不能为空");
        } else if (student.getPassword() == null) {
            return CommonResult.failed("密码不能为空");
        } else if (studentService.updateInfo(student) > 0) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }
    @ApiOperation("删除学生信息")
    @RequestMapping(value = "/{studentId}",method = RequestMethod.DELETE)
    public CommonResult deleteInfo(@PathVariable List<Integer> studentId){
        if(studentId==null){
            return CommonResult.failed("学生Id不能为空");
        }
        else if(studentService.deleteInfos(studentId) >0){
            //return CommonResult.builder().code(200).message("共删除"+number+"个学生信息").build();
            return CommonResult.success();
        }
        return CommonResult.failed();
    }
}
