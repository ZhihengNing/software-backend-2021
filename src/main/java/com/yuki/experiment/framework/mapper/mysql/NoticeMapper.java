package com.yuki.experiment.framework.mapper.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuki.experiment.framework.entity.Notice;
import org.springframework.stereotype.Repository;

/**
* @author 86180
* @description 针对表【notice】的数据库操作Mapper
* @createDate 2021-11-16 22:30:41
* @Entity com.yuki.experiment.framework.entity.Notice
*/
@Repository
public interface NoticeMapper extends BaseMapper<Notice> {
}




