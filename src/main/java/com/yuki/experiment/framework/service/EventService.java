package com.yuki.experiment.framework.service;

import com.alibaba.fastjson.JSONObject;
import com.yuki.experiment.framework.entity.Event;

import java.util.Date;
import java.util.List;

public interface EventService {

    int insertEvent(Event event);

    Event updateEvent(Event event);

    int deleteEvent(int eventId);

    List<JSONObject>getInfo(Date beginDate,Date endDate);
}
