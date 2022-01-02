package com.yuki.experiment.framework.controller;

import com.alibaba.fastjson.JSONObject;
import com.yuki.experiment.common.result.CommonResult;
import com.yuki.experiment.common.utils.EmptyUtil;
import com.yuki.experiment.common.utils.FileUtil;
import com.yuki.experiment.framework.entity.Student;
import com.yuki.experiment.framework.service.CourseScoreService;
import com.yuki.experiment.framework.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/student")
@Api(tags = "学生模块")
@Slf4j
public class StudentController {
    private StudentService studentService;

    private CourseScoreService courseScoreService;

    private static final String STUDENT_PATH="student";

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @Autowired
    public void setCourseScoreService(CourseScoreService courseScoreService) {
        this.courseScoreService = courseScoreService;
    }

    @ApiOperation("查看学生信息")
    @RequestMapping(value = "/studentId/{studentId}",method = RequestMethod.GET)
    public CommonResult<Student> getInfo( @PathVariable Integer studentId) {
        if (studentId == null) {
            return CommonResult.failed("学生Id不能为空");
        }
        return CommonResult.success(studentService.getInfo(studentId));
    }

    @ApiOperation("插入学生信息")
    @RequestMapping(value = "",method = RequestMethod.POST)
    public CommonResult<Student> addStudentInfo(@RequestBody Student student) {
        if (student.getAdministratorId() == null) {
            return CommonResult.failed("管理员Id不能为空");
        } else if (EmptyUtil.isEmpty(student.getName())) {
            return CommonResult.failed("学生姓名不能为空");
        } else if (EmptyUtil.isEmpty(student.getPassword())) {
            return CommonResult.failed("密码不能为空");
        }
        Student student1 = studentService.insertInfo(student);
        if (student1 != null) {
            return CommonResult.success(student1);
        }
        return CommonResult.failed();
    }

    @ApiOperation("更新学生信息")
    @RequestMapping(value = "",method = RequestMethod.PUT)
    public CommonResult<Student> modifyStudentInfo(@RequestBody Student student) {
        if (student.getId() == null) {
            return CommonResult.failed("学生Id不能为空");
        } else if (student.getName() != null && student.getName().equals("")) {
            return CommonResult.failed("学生姓名不能为空");
        } else if (student.getPassword() != null && student.getPassword().equals("")) {
            return CommonResult.failed("密码不能为空");
        }
        Student student1 = studentService.updateInfo(student);
        if (student1 != null) {
            return CommonResult.success(student1);
        }
        return CommonResult.failed();
    }

    @ApiOperation("上传学生头像")
    @RequestMapping(value = "/uploadPic/{studentId}",method = RequestMethod.POST)
    public CommonResult<String> uploadStudentPic(@PathVariable("studentId") Integer studentId,
                                         @RequestPart("file")MultipartFile pic){
        if(studentId==null){
            return CommonResult.failed("学生Id不能为空");
        }
        else if(EmptyUtil.isEmpty(pic)){
            return CommonResult.failed("avatar不能为空");
        }
        String url = FileUtil.generatorUrl(STUDENT_PATH, studentId);
        String webUrl=FileUtil.generatorWebUrl(STUDENT_PATH,studentId);
        String picUrl=studentService.uploadPic(studentId,url,webUrl,pic);
        if(picUrl!=null){
            return CommonResult.success(picUrl);
        }
        return CommonResult.failed();
    }

    @ApiOperation("删除学生信息")
    @RequestMapping(value = "/studentId/{studentId}",method = RequestMethod.DELETE)
    public CommonResult deleteInfo(@PathVariable List<Integer> studentId){
        if(studentId==null){
            return CommonResult.failed("学生Id不能为空");
        }
        else if(studentService.deleteInfos(studentId) >0){
            //return CommonResult.builder().code(200).message("共删除"+number+"个学生信息").build();
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @ApiOperation("查看学生选课的课程")
    @RequestMapping(value = "/takes/{studentId}", method = RequestMethod.GET)
    public CommonResult<List<JSONObject>> getCourseInfo(@PathVariable("studentId") Integer studentId) {
        if (studentId == null) {
            return CommonResult.failed("学生Id不能为空");
        }
        List<JSONObject> courses = courseScoreService.getCourseInfoAndIsActive(studentId);
        return CommonResult.success(courses);
    }

}
