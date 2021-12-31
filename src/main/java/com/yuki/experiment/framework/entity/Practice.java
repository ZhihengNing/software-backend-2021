package com.yuki.experiment.framework.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.List;

@Data
public class Practice {

    private ObjectId id;

    @ApiModelProperty("课程Id")
    private Integer courseId;

    @ApiModelProperty("教师Id")
    private Integer teacherId;

    @ApiModelProperty("每一道题")
    private List<Problem> problems;



}
