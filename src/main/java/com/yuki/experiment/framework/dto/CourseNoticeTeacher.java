package com.yuki.experiment.framework.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CourseNoticeTeacher {

    private Integer id;

    private Integer courseId;

    private String title;

    private String content;

    private Date createTime;

    private Date updateTime;

    private Integer teacherId;

    private String name;

}
