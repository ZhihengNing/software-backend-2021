package com.yuki.experiment.framework.controller;

import com.yuki.experiment.common.result.CommonResult;
import com.yuki.experiment.framework.manage.impl.MailServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.util.UUID;

@RestController
@RequestMapping("/mail")
@Api(tags= "邮箱模块")
@Slf4j
public class MailController {

    private final MailServiceImpl mailService;
    @Autowired
    public MailController(MailServiceImpl mailService){
        this.mailService=mailService;
    }

    @ApiOperation("发送邮件")
    @RequestMapping(value = "/send",method = RequestMethod.POST)
    public CommonResult<Object> sendMail(@RequestParam("studentMailbox")String studentMailbox,
                                         @RequestParam("courseId") Integer courseId) {
        if (studentMailbox == null) {
            return CommonResult.failed("用户邮箱账号不能为空");
        } else if (courseId == null) {
            return CommonResult.failed("课程Id不能为空");
        }
        String activeCode = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        String text = "同济大学教学实验管理平台发来验证码" + activeCode + "来激活课程号为" + courseId + "的课程，请确认是否为本人操作";
        if (mailService.mailSend(studentMailbox,
                "激活码",
                text)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @ApiOperation("发送带附件邮件")
    @RequestMapping(value = "/sendFile",method = RequestMethod.POST)
    public CommonResult<Object> sendMail(@RequestParam("studentMailbox")String studentMailbox,
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

}
