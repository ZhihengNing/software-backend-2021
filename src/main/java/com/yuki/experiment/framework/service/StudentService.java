package com.yuki.experiment.framework.service;

import com.yuki.experiment.framework.entity.Student;

import java.util.List;

public interface StudentService {

    boolean verifyLogin(Integer id,String password);

    Student getInfo(Integer id);

    int insertInfo(Student student);

    int updateInfo(Student student);

    int deleteInfos(List<Integer>studentId);
}
