package com.yuki.experiment.framework.service;

import com.yuki.experiment.framework.entity.Student;

public interface StudentService {

    boolean verifyLogin(Integer id,String password);

    Student getInfo(Integer id);
}
