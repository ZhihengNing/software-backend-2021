package com.yuki.experiment.framework.service;

import com.yuki.experiment.framework.entity.Administrator;

import java.util.List;

public interface TestService {

    List<Administrator> selectList();

    int insert(Administrator administrator);

    int update(Administrator administrator);

    int delete(String name);
}
