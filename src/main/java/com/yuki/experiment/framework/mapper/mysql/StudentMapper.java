package com.yuki.experiment.framework.mapper.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuki.experiment.framework.entity.Student;
import org.springframework.stereotype.Repository;

/**
* @author 86180
* @description 针对表【student】的数据库操作Mapper
* @createDate 2021-11-17 14:10:23
* @Entity com.yuki.experiment.framework.entity.Student
*/
@Repository
public interface StudentMapper extends BaseMapper<Student> {

}




