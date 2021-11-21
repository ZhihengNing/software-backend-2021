package com.yuki.experiment.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuki.experiment.framework.entity.Student;
import com.yuki.experiment.framework.mapper.StudentMapper;
import com.yuki.experiment.framework.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentMapper mapper;
    @Override
    public boolean verifyLogin(Integer id, String password) {
        QueryWrapper<Student>wrapper=new QueryWrapper<>();
        wrapper.eq("id",id)
                .eq("password",password);
        return mapper.selectOne(wrapper)!=null;
    }

    @Override
    public Student getInfo(Integer id) {
        return mapper.selectById(id);
    }


}
