package com.yuki.experiment.framework.service;

import com.yuki.experiment.framework.entity.Administrator;

public interface AdministratorService {

    Administrator getInfo(Integer id);

    int insert(Administrator administrator);

    boolean verifyLogin(Integer id,String password);
}
