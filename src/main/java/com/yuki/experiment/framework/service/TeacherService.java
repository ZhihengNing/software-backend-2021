package com.yuki.experiment.framework.service;

import com.yuki.experiment.framework.entity.Teacher;

public interface TeacherService {

    Teacher getInfo(Integer id);

    boolean verifyLogin(Integer id, String password);

    int insert(Teacher teacher);
}
