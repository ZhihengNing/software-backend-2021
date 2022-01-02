package com.yuki.experiment.framework.mapper.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuki.experiment.framework.dto.CourseFeedbackDTO;
import com.yuki.experiment.framework.entity.CourseFeedback;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author 86180
* @description 针对表【course_feedback】的数据库操作Mapper
* @createDate 2021-11-12 21:43:09
* @Entity com.yuki.experiment.framework.entity.CourseFeedback
*/
@Repository
public interface CourseFeedbackMapper extends BaseMapper<CourseFeedback> {

    List<CourseFeedbackDTO>getFeedback(@Param("courseId")Integer courseId
            ,@Param("studentId")Integer  studentId);

}




