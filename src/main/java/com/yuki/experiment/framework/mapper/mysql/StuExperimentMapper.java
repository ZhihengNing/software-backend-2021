package com.yuki.experiment.framework.mapper.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuki.experiment.framework.entity.StuExperiment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* @author 86180
* @description 针对表【stu_experiment】的数据库操作Mapper
* @createDate 2021-12-29 18:27:03
* @Entity generator.domain.StuExperiment
*/
@Repository
public interface StuExperimentMapper extends BaseMapper<StuExperiment> {

    String getUrl(@Param("studentId")Integer studentId
            ,@Param("experimentId")Integer experimentId);
}




