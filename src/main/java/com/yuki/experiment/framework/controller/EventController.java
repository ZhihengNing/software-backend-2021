package com.yuki.experiment.framework.controller;

import com.alibaba.fastjson.JSONObject;
import com.yuki.experiment.common.result.CommonResult;
import com.yuki.experiment.common.utils.EmptyUtil;
import com.yuki.experiment.framework.entity.Event;
import com.yuki.experiment.framework.service.EventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javafx.collections.ObservableMap;
import org.junit.jupiter.api.Tags;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@Api(tags="日历事件模块")
@RequestMapping("/event")
public class EventController {
    @Resource
    private EventService eventService;

    @ApiOperation("获取事件")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public CommonResult<List<JSONObject>> queryInfo(@RequestParam("beginDate") String beginDate,
                                                    @RequestParam(value = "endDate", required = false) String endDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parseBeginDate;
        endDate=endDate==null?beginDate:endDate;
        Date parseEndDate;
        try {
            parseBeginDate = format.parse(beginDate);
            parseEndDate = format.parse(endDate);
        } catch (ParseException e) {
            return CommonResult.failed("时间格式不正确,请注意时间格式");
        }
        parseEndDate = (parseEndDate == null ? parseBeginDate : parseEndDate);
        if (parseEndDate.getTime() < parseBeginDate.getTime()) {
            return CommonResult.failed("后者时间不能小于前者时间");
        }
        return CommonResult.success(eventService.getInfo(parseBeginDate, parseEndDate));
    }

    @ApiOperation("插入事件")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public CommonResult addEvent(@RequestBody Event event) {
        if (event.getStudentId() == null) {
            return CommonResult.failed("学生Id不能为空");
        } else if (EmptyUtil.isEmpty(event.getTitle())) {
            return CommonResult.failed("title不能为空");
        } else if (event.getDoTime() == null) {
            return CommonResult.failed("要做某件事的时间不能为空");
        }
        if (eventService.insertEvent(event) > 0) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @ApiOperation("更新事件")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public CommonResult<Event> modifyEvent(@RequestBody Event event) {
        if (event.getId() == null) {
            return CommonResult.failed("事件Id不能为空");
        } else if (EmptyUtil.isEmpty(event.getTitle())) {
            return CommonResult.failed("title不能为空");
        } else if (event.getDoTime() == null) {
            return CommonResult.failed("要做某件事的时间不能为空");
        }
        if (eventService.updateEvent(event) != null) {
            return CommonResult.success(event);
        }
        return CommonResult.failed();
    }

    @ApiOperation("删除事件")
    @RequestMapping(value = "/{eventId}", method = RequestMethod.DELETE)
    public CommonResult deleteEvent(@PathVariable("eventId") Integer eventId) {
        if (eventId == null) {
            return CommonResult.failed("事件Id不能为空");
        }
        if (eventService.deleteEvent(eventId) > 0) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }


}
