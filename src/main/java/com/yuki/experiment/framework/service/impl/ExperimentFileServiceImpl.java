package com.yuki.experiment.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuki.experiment.common.utils.FileUtil;
import com.yuki.experiment.framework.dto.FileInfoDTO;
import com.yuki.experiment.framework.entity.Experiment;
import com.yuki.experiment.framework.entity.ExperimentFile;
import com.yuki.experiment.framework.mapper.mysql.ExperimentFileMapper;
import com.yuki.experiment.framework.mapper.mysql.ExperimentMapper;
import com.yuki.experiment.framework.service.ExperimentFileService;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ExperimentFileServiceImpl implements ExperimentFileService {

    private final static String PATH="course";

    @Autowired
    private ExperimentFileMapper experimentFileMapper;
    @Autowired
    private ExperimentMapper experimentMapper;


    @Override
    public List<ExperimentFile> getInfo(Integer experimentId, Integer teacherId,Integer experimentFileId) {
        QueryWrapper<ExperimentFile> wrapper = new QueryWrapper<>();
        wrapper.eq(experimentId != null, "experiment_id", experimentId)
                .eq(teacherId != null, "teacher_id", teacherId)
                .eq(experimentFileId != null, "id", experimentFileId);
        return experimentFileMapper.selectList(wrapper);
    }

    @Override
    public int insert(List<MultipartFile> multipartFiles,Integer experimentId, Integer teacherId) {
        Experiment one = experimentMapper.selectOne(new QueryWrapper<Experiment>().eq("experiment_id", experimentId));
        Integer courseId = one.getCourseId();
        if(courseId!=null) {
            Pair<String, String> twoUrl = FileUtil.generatorTwoUrl(PATH, courseId, experimentId);
            String path = twoUrl.getKey();
            String webPath = twoUrl.getValue();
            List<FileInfoDTO> fileInfoDTOS = FileUtil.preserveFile(multipartFiles, path, webPath);
            for(FileInfoDTO item: fileInfoDTOS){
                String url = item.getFileUrl();
                String name = item.getFileName();
                ExperimentFile build = ExperimentFile.builder()
                        .experimentId(experimentId)
                        .name(name)
                        .url(url)
                        .teacherId(teacherId).build();
                //保存到数据库
                experimentFileMapper.insert(build);
            }
            return 1;
        }
        return 0;
    }
}
