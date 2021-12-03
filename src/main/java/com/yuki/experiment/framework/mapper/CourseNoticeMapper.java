package com.yuki.experiment.framework.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuki.experiment.framework.entity.CourseNotice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
* @author 86180
* @description 针对表【course_notice】的数据库操作Mapper
* @createDate 2021-11-12 21:43:09
* @Entity com.yuki.experiment.framework.entity.CourseNotice
*/
@Repository
public interface CourseNoticeMapper extends BaseMapper<CourseNotice> {

    List<CourseNotice> fuzzyGet(@Param("keyword") String keyword);


}




