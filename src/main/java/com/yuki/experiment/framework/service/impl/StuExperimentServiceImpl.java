package com.yuki.experiment.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuki.experiment.common.utils.FileUtil;
import com.yuki.experiment.framework.dto.FileInfoDTO;
import com.yuki.experiment.framework.dto.StudentGradeDTO;
import com.yuki.experiment.framework.entity.Course;
import com.yuki.experiment.framework.entity.CourseScore;
import com.yuki.experiment.framework.entity.StuExperiment;
import com.yuki.experiment.framework.mapper.CourseMapper;
import com.yuki.experiment.framework.mapper.CourseScoreMapper;
import com.yuki.experiment.framework.mapper.StuExperimentMapper;
import com.yuki.experiment.framework.service.StuExperimentService;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class StuExperimentServiceImpl implements StuExperimentService {

    private final static String experimentFileUploadPath = "experiment";

    private StuExperimentMapper stuExperimentMapper;


    private CourseScoreMapper courseScoreMapper;

    private CourseMapper courseMapper;

    @Autowired
    public void setStuExperimentMapper(StuExperimentMapper stuExperimentMapper) {
        this.stuExperimentMapper = stuExperimentMapper;
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

        stuExperiment.setUrl(url);
        log.info(name + "成功保存到数据库！");
        //插入到stu_experiment表进行保存
        return stuExperimentMapper.insert(stuExperiment);
    }


    @Override
    public StuExperiment update(MultipartFile multipartFile, Integer studentId, Integer experimentId, String jobContent) {
        Pair<String, String> twoUrl = FileUtil.generatorTwoUrl(experimentFileUploadPath, experimentId, studentId);
        String path = twoUrl.getKey();
        String webPath = twoUrl.getValue();
        //在服务器删除原来文件的url
        String originalUrl = stuExperimentMapper.getUrl(studentId, experimentId);
        if (originalUrl != null) {
            FileUtil.deleteFile(originalUrl);
        }
        //把新的文件url存入服务器
        FileInfoDTO fileInfoDTO = FileUtil.preserveFile(multipartFile, path, webPath);
        String url = fileInfoDTO.getFileUrl();
        String name = fileInfoDTO.getFileName();
        log.info(name + "成功替换原文件,存到数据库中");
        //这里更新stu_experiment
        StuExperiment build = StuExperiment.builder().experimentId(experimentId).studentId(studentId)
                .jobContent(jobContent).url(url).build();
        if (stuExperimentMapper.updateById(build) > 0) {
            return build;
        }
        return null;
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

    private static List<BigDecimal> transfer(String scoreRatio){
        String[] split = scoreRatio.split(",");
        List<BigDecimal>list=new ArrayList<>();
        for (String s : split) {
            list.add(BigDecimal.valueOf(Double.parseDouble(s)));
        }
        return list;
    }
    @Override
    public StudentGradeDTO getStudentGrade(Integer studentId, Integer courseId) {
//        QueryWrapper<CourseScore> wrapper = new QueryWrapper<>();
//        wrapper.eq("student_id", studentId).eq("course_id", courseId);
//        CourseScore StudentGrade = courseScoreMapper.selectOne(wrapper);
//        //得到某门课的考勤得分
//        BigDecimal attendanceScore = StudentGrade.getAttendanceScore() == null ?
//                new BigDecimal(0) : StudentGrade.getAttendanceScore();
//TODO

        QueryWrapper<CourseScore> courseScoreQueryWrapper = new QueryWrapper<>();
        courseScoreQueryWrapper.eq("student_id", studentId).eq("course_id", courseId);
        CourseScore StudentGrade = courseScoreMapper.selectOne(courseScoreQueryWrapper);

        //计算考勤相关
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("id", courseId);
        Course course = courseMapper.selectOne(courseQueryWrapper);
        String scoreRatio = course.getScoreRatio();
        List<BigDecimal> transfer = transfer(scoreRatio);
        BigDecimal attendance = StudentGrade.getAttendanceScore().multiply(transfer.get(0));

        //计算实验相关
        QueryWrapper<StuExperiment> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("student_id", studentId);
        List<StuExperiment> stuExperiments = stuExperimentMapper.selectList(wrapper1);
        BigDecimal start = BigDecimal.ZERO;
        for (StuExperiment item : stuExperiments) {
            start = start.add(item.getExperimentScore());
        }

        BigDecimal experiments = start.multiply(transfer.get(1));
        //计算对抗练习相关


        BigDecimal practices = null;

        StudentGrade.setCourseScore(attendance.add(experiments).add(practices));

        StudentGradeDTO build = StudentGradeDTO.builder().courseScore(StudentGrade)
                .experimentScore(stuExperiments).build();


        return build;
    }
}
