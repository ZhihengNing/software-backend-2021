package com.yuki.experiment.framework.controller;

import com.yuki.experiment.common.result.CommonResult;
import com.yuki.experiment.framework.entity.Notice;
import com.yuki.experiment.framework.service.NoticeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @ApiOperation("获取系统公告")
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public CommonResult<List<Notice>> getNoticeInfo() {
        return CommonResult.success(noticeService.getAllNotice());
    }
}
