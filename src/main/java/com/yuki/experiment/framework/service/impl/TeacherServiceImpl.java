package com.yuki.experiment.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuki.experiment.framework.entity.Teacher;
import com.yuki.experiment.framework.mapper.mysql.TeacherMapper;
import com.yuki.experiment.framework.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherMapper mapper;

    @Override
    public Teacher getInfo(Integer id) {
        return mapper.selectById(id);
    }

    @Override
    public boolean verifyLogin(Integer id, String password) {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id)
                .eq("password", password);
        return mapper.selectOne(wrapper) != null;
    }
    @Override
    public int insert(Teacher teacher){
        return mapper.insert(teacher);
    }

}
