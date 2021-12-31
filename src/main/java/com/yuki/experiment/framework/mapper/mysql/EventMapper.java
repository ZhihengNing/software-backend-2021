package com.yuki.experiment.framework.mapper.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuki.experiment.framework.entity.Event;
import org.springframework.stereotype.Repository;

/**
* @author 86180
* @description 针对表【event】的数据库操作Mapper
* @createDate 2021-12-24 19:26:21
* @Entity com.yuki.experiment.framework.entity.Event
*/
@Repository
public interface EventMapper extends BaseMapper<Event> {

}




