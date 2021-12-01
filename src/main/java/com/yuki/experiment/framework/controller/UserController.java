package com.yuki.experiment.framework.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.yuki.experiment.common.result.CommonResult;
import com.yuki.experiment.common.utils.JwtUtil;
import com.yuki.experiment.framework.service.AdministratorService;
import com.yuki.experiment.framework.service.StudentService;
import com.yuki.experiment.framework.service.TeacherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
    private final StudentService studentService;

    private final AdministratorService administratorService;

    private final TeacherService teacherService;

    @Autowired
    public UserController(StudentService studentService, AdministratorService administratorService, TeacherService teacherService) {
        this.studentService = studentService;
        this.administratorService = administratorService;
        this.teacherService = teacherService;
    }


    @ApiOperation("登录接口,返回token")
    @DynamicParameters(name = "userInfo", properties = {
            @DynamicParameter(name = "id", value = "用户id", dataTypeClass = Integer.class, example = "1", required = true),
            @DynamicParameter(name = "password", value = "密码", dataTypeClass = String.class, example = "as661778", required = true),
            @DynamicParameter(name = "type", value = "用户类型", dataTypeClass = Integer.class, example = "1", required = true)
    })
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResult<JSONObject> login(@RequestBody JSONObject user) {
        int type = user.getIntValue("type");
        Integer id = user.getInteger("id");
        String password = user.getString("password");
        if (id == null || password == null) {
            return CommonResult.failed("用户名或密码为空");
        }
        String token = JwtUtil.getToken(user);
        JSONObject json = new JSONObject();
        //管理员
        if (type == 0 && administratorService.verifyLogin(id, password)) {
            json.put("token", token);
            json.put("Info", administratorService.getInfo(id));
            return CommonResult.success(json);
        } //教师
        else if (type == 1 && teacherService.verifyLogin(id, password)) {
            json.put("token", token);
            json.put("Info", teacherService.getInfo(id));
            return CommonResult.success(json);
        }//学生
        else if (type == 2 && studentService.verifyLogin(id, password)) {
            json.put("token", token);
            json.put("Info", studentService.getInfo(id));
            return CommonResult.success(json);
        } else {
            return CommonResult.failed("用户名或密码不正确");
        }
    }

    @ApiOperation("校验用户权限")
    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    public CommonResult<Object> verify(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        try {
            JwtUtil.checkToken(token);
        } catch (Exception e) {
            return CommonResult.unauthorized();
        }
        return CommonResult.success();
    }
}
