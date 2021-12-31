package com.yuki.experiment.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yuki.experiment.framework.entity.Administrator;
import com.yuki.experiment.framework.mapper.mysql.AdministratorMapper;
import com.yuki.experiment.framework.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TestServiceImpl implements TestService {

    private final AdministratorMapper mapper;

    @Autowired
    public TestServiceImpl(AdministratorMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Administrator> selectList() {
        QueryWrapper<Administrator> wrapper = new QueryWrapper<>();
        return mapper.selectList(wrapper);
    }

    @Override
    public int insert(Administrator administrator) {
        int insert = mapper.insert(administrator);
        log.info("新用户ID为：{}",administrator.getId());
        return insert;
    }

    @Override
    public int update(Administrator administrator) {
        UpdateWrapper<Administrator> wrapper = new UpdateWrapper<>();
        //用下面的方式才能更新字段为null，但是不推荐使用，因为你不知道前端传进来的字段是null，
        // 还是没传这个字段
//        wrapper.set(administrator.getName() == null, "name", null)
//                .set(administrator.getSex() == null, "sex", null);
        //.eq("name","nzh");
        return mapper.updateById(administrator);
    }

    @Override
    public int delete(String name) {
        QueryWrapper<Administrator> wrapper=new QueryWrapper<>();
        wrapper.eq(name!=null,"name",name);
        return mapper.delete(wrapper);
    }
}
