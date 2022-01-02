package com.yuki.experiment.framework.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class CourseFeedbackDTO {

    @ApiModelProperty("课程反馈Id")
    private Integer courseFeedbackId;


    @ApiModelProperty("学生ID")
    private Integer studentId;


    @ApiModelProperty("课程ID")
    private Integer courseId;


    @ApiModelProperty("标题")
    private String title;


    @ApiModelProperty("内容")
    private String content;


    @ApiModelProperty("课程反馈创建时间")
    private Date createTime;


    @ApiModelProperty("课程反馈修改时间")
    private Date updateTime;

    @ApiModelProperty("学生名字")
    private String studentName;


}
