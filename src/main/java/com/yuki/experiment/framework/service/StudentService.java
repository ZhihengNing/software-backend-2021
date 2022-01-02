package com.yuki.experiment.framework.service;

import com.yuki.experiment.framework.entity.Student;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentService {

    boolean verifyLogin(Integer id,String password);

    Student getInfo(Integer id);

    Student insertInfo(Student student);

    Student updateInfo(Student student);

    int deleteInfos(List<Integer>studentId);

    String uploadPic(Integer studetId,String url, String webUrl, MultipartFile multipartFile);
}
