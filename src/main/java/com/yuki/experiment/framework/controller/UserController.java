package com.yuki.experiment.framework.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.yuki.experiment.common.result.CommonResult;
import com.yuki.experiment.common.role.MyRole;
import com.yuki.experiment.common.utils.EmptyUtil;
import com.yuki.experiment.common.utils.FileUtil;
import com.yuki.experiment.common.utils.JwtUtil;
import com.yuki.experiment.framework.entity.Administrator;
import com.yuki.experiment.framework.entity.Student;
import com.yuki.experiment.framework.entity.Teacher;
import com.yuki.experiment.framework.manage.impl.MailServiceImpl;
import com.yuki.experiment.framework.service.AdministratorService;
import com.yuki.experiment.framework.service.StudentService;
import com.yuki.experiment.framework.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@Api(tags = "用户模块")
@Slf4j
public class UserController {
    private final StudentService studentService;

    private final AdministratorService administratorService;

    private final TeacherService teacherService;

    private final MailServiceImpl mailService;

    @Autowired
    public UserController(StudentService studentService, AdministratorService administratorService, TeacherService teacherService, MailServiceImpl mailService) {
        this.studentService = studentService;
        this.administratorService = administratorService;
        this.teacherService = teacherService;
        this.mailService = mailService;
    }

    @ApiOperation("登录接口,返回token")
    @DynamicParameters(name = "userInfo", properties = {
            @DynamicParameter(name = "id", value = "用户id", dataTypeClass = Integer.class, example = "200000", required = true),
            @DynamicParameter(name = "password", value = "密码", dataTypeClass = String.class, example = "123456", required = true),
            @DynamicParameter(name = "type", value = "用户类型", dataTypeClass = String.class, example = "学生", required = true)
    })
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResult<JSONObject> login(@RequestBody JSONObject user) {
        String type = user.getString("type");
        Integer id = user.getInteger("id");
        String password = user.getString("password");
        if (id == null || EmptyUtil.isEmpty(password)) {
            return CommonResult.failed("用户名或密码为空");
        }
        String token = JwtUtil.getToken(user);
        JSONObject json = new JSONObject();
        //管理员
        if (MyRole.ADMINISTRATOR.getRoleName().equals(type) && administratorService.verifyLogin(id, password)) {
            json.put("token", token);
            json.put("Info", administratorService.getInfo(id));
            return CommonResult.success(json);
        } //教师
        else if (MyRole.TEACHER.getRoleName().equals(type) && teacherService.verifyLogin(id, password)) {
            json.put("token", token);
            json.put("Info", teacherService.getInfo(id));
            return CommonResult.success(json);
        }//学生
        else if (MyRole.STUDENT.getRoleName().equals(type) && studentService.verifyLogin(id, password)) {
            json.put("token", token);
            json.put("Info", studentService.getInfo(id));
            return CommonResult.success(json);
        } else {
            return CommonResult.failed("用户名或密码不正确");
        }
    }

    @ApiOperation("校验用户权限")
    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    public CommonResult verify(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        try {
            JwtUtil.checkToken(token);
        } catch (Exception e) {
            return CommonResult.unauthorized();
        }
        return CommonResult.success();
    }

    @ApiOperation("发送邮件")
    @RequestMapping(value = "/email/send",method = RequestMethod.POST)
    public CommonResult<String> sendMail(@RequestParam("studentMailbox")String studentMailbox,
                                         @RequestParam("courseId") Integer courseId) {
        if (EmptyUtil.isEmpty(studentMailbox)) {
            return CommonResult.failed("用户邮箱账号不能为空");
        } else if (courseId == null) {
            return CommonResult.failed("课程Id不能为空");
        }
        String activeCode = UUID.randomUUID().toString().replace("-", "").toUpperCase().substring(0, 6);
        String text = "【同济大学教学实验管理平台】您的课程(courseId:" + courseId + ")验证码是："
                + activeCode +
                "，有效时间为10分钟,请尽快认证";
        if (mailService.mailSend(studentMailbox,
                "课程激活码",
                text)) {
            return CommonResult.success(activeCode);
        }
        return CommonResult.failed();
    }

    @ApiOperation("发送带附件邮件")
    @RequestMapping(value = "/email/sendFile",method = RequestMethod.POST)
    public CommonResult sendMailWithFile(@RequestParam("studentMailbox")String studentMailbox,
                                                 @RequestPart("file") MultipartFile[] files) {
        try {
            if (mailService.mailSend(studentMailbox,
                    "yzh",
                    "email for you",
                    files)) {
                return CommonResult.success();
            }
        } catch (MessagingException e) {
            log.info(e.getMessage());
        }
        return CommonResult.failed();
    }

    @ApiOperation("下载文件")
    @RequestMapping(value = "/download",method = RequestMethod.GET)
    public void testDownload(@RequestParam("url")String url, HttpServletResponse response) {
        if(EmptyUtil.isEmpty(url)){
            return;
        }
        //若下载文件报错，就删除上面那句话
        String name = url.substring(url.lastIndexOf("/") + 1, url.length());

        response.setContentType("application/force-download");
        response.addHeader("Content-Disposition", "attachment; fileName=" +
                new String(name.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));

        FileUtil.downloadFile(url, response);
    }

    @ApiOperation("这个不要用")
    @RequestMapping(value = "/{teacherName}",method = RequestMethod.POST)
    public CommonResult<Boolean> insert(@PathVariable String teacherName) {
        Teacher teacher = new Teacher();
        teacher.setAdministratorId(1);
        teacher.setGender("男");
        teacher.setName(teacherName);
        teacher.setPassword("123456");
        teacherService.insert(teacher);
        return CommonResult.success(true);
    }

    @ApiOperation("获取用户信息")
    @RequestMapping(value = "",method = RequestMethod.GET)
    public CommonResult queryInfo(@RequestParam("type")String type,
                                  @RequestParam("id")Integer id) {
        if (MyRole.TEACHER.getRoleName().equals(type)) {
            Teacher info = teacherService.getInfo(id);
            if(info==null){
                return CommonResult.failed();
            }
            return CommonResult.success(info);
        } else if (MyRole.STUDENT.getRoleName().equals(type)) {
            Student info = studentService.getInfo(id);
            if(info==null){
                return CommonResult.failed();
            }
            return CommonResult.success(info);

        } else if(MyRole.ADMINISTRATOR.getRoleName().equals(type)){
            Administrator info = administratorService.getInfo(id);
            if(info==null){
                return CommonResult.failed();
            }
            return CommonResult.success(info);
        }
        return CommonResult.failed("没有这个类型的用户");

    }

}
