package com.yuki.experiment.framework.service;

import com.yuki.experiment.framework.entity.Administrator;

public interface AdministratorService {

    Administrator getInfo(Integer id);

    boolean verifyLogin(Integer id,String password);
}
