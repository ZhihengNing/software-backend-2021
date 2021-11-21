package com.yuki.experiment.framework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sun.mail.imap.protocol.BASE64MailboxDecoder;
import com.yuki.experiment.framework.entity.Course;
import org.springframework.stereotype.Repository;

/**
* @author 86180
* @description 针对表【course】的数据库操作Mapper
* @createDate 2021-11-16 23:26:40
* @Entity com.yuki.experiment.framework.entity.Course
*/
@Repository
public interface CourseMapper extends BaseMapper<Course> {

}




