package com.yuki.experiment.framework.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuki.experiment.common.utils.DateUtil;
import com.yuki.experiment.framework.entity.Event;
import com.yuki.experiment.framework.entity.Experiment;
import com.yuki.experiment.framework.entity.Practice;
import com.yuki.experiment.framework.mapper.mysql.EventMapper;
import com.yuki.experiment.framework.mapper.mysql.ExperimentMapper;
import com.yuki.experiment.framework.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private EventMapper eventMapper;

    private ExperimentMapper experimentMapper;


    @Autowired
    public void setEventMapper(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }

    @Autowired
    public void setExperimentMapper(ExperimentMapper experimentMapper) {
        this.experimentMapper = experimentMapper;
    }


    @Override
    public List<JSONObject> getInfo(Date beginDate, Date endDate) {
        QueryWrapper<Experiment> wrapper = new QueryWrapper<>();
        wrapper.between("experiment_deadline", beginDate, endDate);
        List<Experiment> experimentList = experimentMapper.selectList(wrapper);

        QueryWrapper<Event> eventQueryWrapper = new QueryWrapper<>();
        wrapper.between("do_time", beginDate, endDate);
        List<Event> eventList = eventMapper.selectList(eventQueryWrapper);

//        QueryWrapper<Practice> practiceQueryWrapper = new QueryWrapper<>();
//        practiceQueryWrapper.between("end_time", beginDate, endDate);
//        List<Practice> practiceList = practiceMapper.selectList(practiceQueryWrapper);

        Calendar begin = Calendar.getInstance();
        begin.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        begin.setTime(beginDate);
        List<JSONObject> jsonList = new ArrayList<>();


        for (Calendar item = begin; item.getTime().getTime() <= endDate.getTime();
             item.add(Calendar.DAY_OF_YEAR, 1)) {
            JSONObject json = new JSONObject();
            json.put("date", item.getTime());
            List<Experiment> experiments = experimentList.stream()
                    .filter((s) -> DateUtil.equals(s.getExperimentDeadline(), item))
                    .collect(Collectors.toList());
            json.put("experiment", experiments);

            List<Event> events = eventList.stream()
                    .filter((s) -> DateUtil.equals(s.getDoTime(), item))
                    .collect(Collectors.toList());
            json.put("event", events);

//            List<Practice> practices = practiceList.stream()
//                    .filter((s) -> DateUtil.equals(s.getEndTime(), item))
//                    .collect(Collectors.toList());
//            json.put("practice", practices);
            jsonList.add(json);
        }
        return jsonList;
    }


    @Override
    public int insertEvent(Event event) {
        return eventMapper.insert(event);
    }

    @Override
    public Event updateEvent(Event event) {
        if(eventMapper.updateById(event)>0){
            return event;
        }
        return null;
    }

    @Override
    public int deleteEvent(int eventId) {
        return eventMapper.deleteById(eventId);
    }

}
