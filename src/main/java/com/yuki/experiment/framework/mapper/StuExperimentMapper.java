package com.yuki.experiment.framework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuki.experiment.framework.entity.StuExperiment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author 86180
* @description 针对表【stu_experiment】的数据库操作Mapper
* @createDate 2021-11-12 21:43:09
* @Entity com.yuki.experiment.framework.entity.StuExperiment
*/
@Repository
public interface StuExperimentMapper extends BaseMapper<StuExperiment> {

    String getUrl(@Param("studentId")Integer studentId, @Param("experimentId") Integer experimentId);

}




