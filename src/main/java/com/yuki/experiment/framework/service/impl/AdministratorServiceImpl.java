package com.yuki.experiment.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuki.experiment.framework.entity.Administrator;
import com.yuki.experiment.framework.mapper.mysql.AdministratorMapper;
import com.yuki.experiment.framework.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorServiceImpl implements AdministratorService {

    @Autowired
    private AdministratorMapper mapper;

    @Override
    public Administrator getInfo(Integer id) {
        return mapper.selectById(id);
    }

    @Override
    public int insert(Administrator administrator) {
        return mapper.insert(administrator);
    }

    @Override
    public boolean verifyLogin(Integer id, String password) {
        QueryWrapper<Administrator>wrapper=new QueryWrapper<>();
        wrapper.eq("id",id)
                .eq("password",password);
        return mapper.selectOne(wrapper)!=null;
    }
}
