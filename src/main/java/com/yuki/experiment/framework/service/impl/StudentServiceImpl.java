package com.yuki.experiment.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuki.experiment.common.utils.FileUtil;
import com.yuki.experiment.framework.dto.FileInfo;
import com.yuki.experiment.framework.entity.Student;
import com.yuki.experiment.framework.mapper.StudentMapper;
import com.yuki.experiment.framework.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentMapper studentMapper;
    @Override
    public boolean verifyLogin(Integer id, String password) {
        QueryWrapper<Student>wrapper=new QueryWrapper<>();
        wrapper.eq("id",id)
                .eq("password",password);
        return studentMapper.selectOne(wrapper)!=null;
    }

    @Override
    public Student getInfo(Integer id) {
        return studentMapper.selectById(id);
    }

    @Override
    public int insertInfo(Student student) {
        return studentMapper.insert(student);
    }

    @Override
    public int updateInfo(Student student) {
        return studentMapper.updateById(student);
    }

    @Override
    public int deleteInfos(List<Integer> studentId) {
        return studentMapper.deleteBatchIds(studentId);
    }

    @Override
    public String uploadPic(Integer studentId,String url, String webUrl, MultipartFile multipartFile) {
        FileInfo fileInfo = FileUtil.preserveFile(multipartFile, url, webUrl);
        String fileUrl = fileInfo.getFileUrl();
        Student student = new Student();
        student.setId(studentId);
        student.setUrl(fileUrl);
        if (studentMapper.updateById(student) > 0) {
            return fileUrl;
        }
        return null;
    }


}
