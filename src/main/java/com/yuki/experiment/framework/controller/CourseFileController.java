package com.yuki.experiment.framework.controller;

import com.alibaba.fastjson.JSONObject;
import com.yuki.experiment.common.result.CommonResult;
import com.yuki.experiment.common.utils.FileUtil;
import com.yuki.experiment.framework.entity.CourseFile;
import com.yuki.experiment.framework.service.CourseFileService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/courseFile")
public class CourseFileController {

    private CourseFileService courseFileService;
    @Autowired
    public void setCourseFileService(CourseFileService courseFileService) {
        this.courseFileService = courseFileService;
    }

    private final static String courseFileUploadPath = "course";

    @ApiOperation("上传课程文件")
    @RequestMapping(value = "/{courseId}/{teacherId}", method = RequestMethod.POST)
    public CommonResult<List<String>> uploadFile(@RequestPart("courseFile") List<MultipartFile> multipartFiles,
                                                 @PathVariable("courseId") Integer courseId,
                                                 @PathVariable("teacherId") Integer teacherId) {
        if (multipartFiles == null) {
            return CommonResult.failed();
        }
        List<String> result = new ArrayList<>();
        //生成文件夹的路径
        String path = FileUtil.generatorUrl(courseFileUploadPath, courseId);

        String webPath=FileUtil.generatorWebUrl(courseFileUploadPath,courseId);
        //保存到服务器
        List<JSONObject> list = FileUtil.preserveFile(multipartFiles, path,webPath);
        for (JSONObject item : list) {
            String url = item.getString("data");
            String name = item.getString("name");
            //保存到数据库
            if (url != null&&courseFileService.insertFile(name, courseId, teacherId, url)>0) {
                result.add(item.getString("message"));
            }
        }
        return CommonResult.success(result);
    }
    @ApiOperation("查看课程文件")
    @RequestMapping(value = "/course/{courseId}",method = RequestMethod.GET)
    public CommonResult<List<CourseFile>> getFileListByCourseId(@PathVariable Integer courseId){
        if(courseId==null){
            return CommonResult.failed("课程Id不能为空");
        }
        List<CourseFile> courseFileByCourseId = courseFileService.getCourseFileByCourseId(courseId);
        return CommonResult.success(courseFileByCourseId);
    }

    @ApiOperation("查看课程文件")
    @RequestMapping(value = "/teacher/{teacherId}",method = RequestMethod.GET)
    public CommonResult<List<CourseFile>> getFileListByTeacherId(@PathVariable Integer teacherId){
        if(teacherId==null){
            return CommonResult.failed("教师Id不能为空");
        }
        List<CourseFile> courseFileByTeacherId = courseFileService.getCourseFileByTeacherId(teacherId);
        return CommonResult.success(courseFileByTeacherId);
    }

    @ApiOperation("删除指定文件")
    @RequestMapping(value = "/{fileIds}",method = RequestMethod.DELETE)
    public CommonResult deleteFile(@PathVariable List<Integer> fileIds) {
        if(courseFileService.deleteFile(fileIds)>0){
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

}
