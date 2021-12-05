package com.yuki.experiment.framework.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuki.experiment.framework.entity.TeacherCourse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author 86180
* @description 针对表【teacher_course】的数据库操作Mapper
* @createDate 2021-12-05 13:04:39
* @Entity com.yuki.experiment.framework.entity.TeacherCourse
*/
@Repository
public interface TeacherCourseMapper extends BaseMapper<TeacherCourse> {
    List<JSONObject> getInfo(@Param("courseId")Integer courseId);
}




