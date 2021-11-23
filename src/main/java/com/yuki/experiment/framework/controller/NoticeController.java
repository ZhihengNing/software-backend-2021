package com.yuki.experiment.framework.controller;

import com.yuki.experiment.common.result.CommonResult;
import com.yuki.experiment.framework.entity.Notice;
import com.yuki.experiment.framework.service.NoticeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    private NoticeService noticeService;

    @Autowired
    public void setNoticeService(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @ApiOperation("获取系统公告")
    @RequestMapping(value = "",method = RequestMethod.GET)
    public CommonResult<List<Notice>> getNoticeInfo() {
        return CommonResult.success(noticeService.getAllNotice());
    }

    @ApiOperation("添加系统公告")
    @RequestMapping(value = "",method = RequestMethod.POST)
    public CommonResult insertNotice(@RequestBody Notice notice){
        if(notice.getAdministratorId()==null){
            return CommonResult.failed("公告创建人Id不能为空");
        }
        else if(noticeService.insertNotice(notice)>0){
            return CommonResult.success();
        }
        return CommonResult.failed();
    }
    @ApiOperation("更新系统公告")
    @RequestMapping(value = "",method = RequestMethod.PUT)
    public CommonResult updateNotice(@RequestBody Notice notice){
        if(notice.getId()==null){
            return CommonResult.failed("公告Id不能为空");
        }
        else if(notice.getAdministratorId()==null){
            return CommonResult.failed("公告修改人Id不能为空");
        }
        else if(noticeService.updateNotice(notice)>0){
            return CommonResult.success();
        }
        return CommonResult.failed();
    }
    @ApiOperation("删除系统公告")
    @RequestMapping(value = "/{noticeId}",method = RequestMethod.DELETE)
    public CommonResult deleteNotice(@PathVariable List<Integer> noticeId) {
        if (noticeService.deleteNotice(noticeId) > 0) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }


}
