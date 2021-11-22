package com.yuki.experiment.framework.controller;

import com.yuki.experiment.common.result.CommonResult;
import com.yuki.experiment.framework.entity.Teacher;
import com.yuki.experiment.framework.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teacher")
@Slf4j
public class TeacherController {

    private TeacherService teacherService;
    @Autowired
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @RequestMapping(value = "/info/{teacherName}",method = RequestMethod.POST)
    public CommonResult<Boolean> insert(@PathVariable String teacherName) {
        Teacher teacher = new Teacher();
        teacher.setAdministratorId(1);
        teacher.setGender("男");
        teacher.setName(teacherName);
        teacher.setPassword("123456");
        teacherService.insert(teacher);
        return CommonResult.success(true);
    }
}
