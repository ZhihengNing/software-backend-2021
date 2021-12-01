package com.yuki.experiment.framework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuki.experiment.framework.entity.Practice;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

/**
* @author 86180
* @description 针对表【practice】的数据库操作Mapper
* @createDate 2021-12-02 00:09:21
* @Entity generator.domain.Practice
*/
@Repository
public interface PracticeMapper extends BaseMapper<Practice> {

}




