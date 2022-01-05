package com.yuki.experiment.framework.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.Date;

@Data
public class CourseNoticeTeacherDTO {

    @ApiModelProperty("课程公告Id")
    private Integer id;

    @ApiModelProperty("课程Id")
    private Integer courseId;

    @ApiModelProperty("公告标题")
    private String title;

    @ApiModelProperty("公告内容")
    private String content;

    @ApiModelProperty("公告创建时间")
    private Date createTime;

    @ApiModelProperty("公告更新时间")
    private Date updateTime;

    @ApiModelProperty("教师Id")
    private Integer teacherId;

    @ApiModelProperty("教师名字")
    private String name;

}
