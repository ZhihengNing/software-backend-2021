package com.yuki.experiment.framework.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yuki.experiment.common.utils.FileUtil;
import com.yuki.experiment.framework.dto.FileInfoDTO;
import com.yuki.experiment.framework.entity.CourseScore;
import com.yuki.experiment.framework.entity.StuExperiment;
import com.yuki.experiment.framework.entity.StudentUploadFile;
import com.yuki.experiment.framework.mapper.CourseMapper;
import com.yuki.experiment.framework.mapper.CourseScoreMapper;
import com.yuki.experiment.framework.mapper.StuExperimentMapper;
import com.yuki.experiment.framework.mapper.StudentUploadFileMapper;
import com.yuki.experiment.framework.service.StuExperimentService;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StuExperimentServiceImpl implements StuExperimentService {

    private final static String experimentFileUploadPath = "experiment";

    private StuExperimentMapper stuExperimentMapper;

    private StudentUploadFileMapper studentUploadFileMapper;

    private CourseScoreMapper courseScoreMapper;

    private CourseMapper courseMapper;

    @Autowired
    public void setStuExperimentMapper(StuExperimentMapper stuExperimentMapper) {
        this.stuExperimentMapper = stuExperimentMapper;
    }

    @Autowired
    public void setStudentUploadFileMapper(StudentUploadFileMapper studentUploadFileMapper) {
        this.studentUploadFileMapper = studentUploadFileMapper;
    }
    @Autowired
    public void setCourseScoreMapper(CourseScoreMapper courseScoreMapper) {
        this.courseScoreMapper = courseScoreMapper;
    }

    @Autowired
    public void setCourseMapper(CourseMapper courseMapper){
        this.courseMapper=courseMapper;
    }

    @Override
    public List<StuExperiment> getStuExperiment(Integer studentId, Integer experimentId) {
        QueryWrapper<StuExperiment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(experimentId!=null,"experiment_id", experimentId)
                .eq(studentId!=null,"student_id", studentId);
        return stuExperimentMapper.selectList(queryWrapper);
    }

    @Override
    public int insert(MultipartFile multipartFile, StuExperiment stuExperiment) {
        Integer experimentId = stuExperiment.getExperimentId();
        Integer studentId = stuExperiment.getStudentId();
        Pair<String, String> twoUrl = FileUtil.generatorTwoUrl(experimentFileUploadPath, experimentId, studentId);
        String path = twoUrl.getKey();
        String webPath = twoUrl.getValue();
        //保存到服务器
        FileInfoDTO fileInfoDTO = FileUtil.preserveFile(multipartFile, path, webPath);
        String url = fileInfoDTO.getFileUrl();
        String name = fileInfoDTO.getFileName();
        StudentUploadFile file = new StudentUploadFile();
        file.setName(name);
        file.setUrl(url);
        if (file.getId() != null) {
            stuExperiment.setFileId(file.getId());
            log.info(name + "成功保存到数据库！");
            //插入到stu_experiment表进行保存
            if (stuExperimentMapper.insert(stuExperiment) > 0) {
                //插入到student_upload_file表进行保存
                return studentUploadFileMapper.insert(file);
            }
        }
        return 0;
    }


    @Override
    public int update(MultipartFile multipartFile, Integer studentId,Integer experimentId,String jobContent) {
        Pair<String, String> twoUrl = FileUtil.generatorTwoUrl(experimentFileUploadPath, experimentId, studentId);
        String path = twoUrl.getKey();
        String webPath = twoUrl.getValue();
        //在服务器删除原来文件的url
        Integer fileId = stuExperimentMapper.selectOne(new QueryWrapper<StuExperiment>()
                .eq("student_id", studentId)
                .eq("experiment_id", experimentId)).getFileId();
        String url=stuExperimentMapper.getUrl(studentId, experimentId);
        if (url != null) {
            FileUtil.deleteFile(url);
        }
        //把新的文件url存入服务器
        FileInfoDTO fileInfoDTO = FileUtil.preserveFile(multipartFile, path, webPath);
        String data = fileInfoDTO.getFileUrl();
        String name = fileInfoDTO.getFileName();
        //这里要更新student_upload_file
        StudentUploadFile temp = new StudentUploadFile();
        temp.setId(fileId);
        temp.setName(name);
        temp.setUrl(data);
        if (studentUploadFileMapper.updateById(temp) > 0) {
            log.info(name + "成功替换原文件,存到数据库中");
            //这里更新stu_experiment
            UpdateWrapper<StuExperiment> wrapper = new UpdateWrapper<>();
            //.set(stuExperiment.getExperimentScore() == null, "experiment_score", null)
            //.set(stuExperiment.getFileId() == null, "file_id", null)
            wrapper.set(jobContent != null, "job_content", jobContent)
                    .eq("student_id", studentId)
                    .eq("experiment_id", experimentId);
            return stuExperimentMapper.update(null, wrapper);
        }
        return 0;
    }

    @Override
    public int uploadGrade(Integer studentId, Integer experimentId, BigDecimal grade) {
        StuExperiment build = StuExperiment.builder()
                .experimentId(experimentId)
                .studentId(studentId)
                .experimentScore(grade)
                .build();
        return stuExperimentMapper.updateById(build);
    }

    @Override
    public JSONObject getStudentGrade(Integer studentId, Integer courseId) {
//        QueryWrapper<CourseScore> wrapper = new QueryWrapper<>();
//        wrapper.eq("student_id", studentId).eq("course_id", courseId);
//        CourseScore courseScore = courseScoreMapper.selectOne(wrapper);
//        //得到某门课的考勤得分
//        BigDecimal attendanceScore = courseScore.getAttendanceScore() == null ?
//                new BigDecimal(0) : courseScore.getAttendanceScore();
//TODO


        QueryWrapper<CourseScore>courseScoreQueryWrapper=new QueryWrapper<>();
        courseScoreQueryWrapper.eq("student_id",studentId).eq("course_id",courseId);
        CourseScore courseScore = courseScoreMapper.selectOne(courseScoreQueryWrapper);


        QueryWrapper<StuExperiment> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("student_id", studentId);
        List<StuExperiment> stuExperiments = stuExperimentMapper.selectList(wrapper1);
        return stuExperiments;
    }
}
