package com.yuki.experiment.framework.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class StuExperimentDTO {

    @ApiModelProperty("学生Id")
    private Integer studentId;

    @ApiModelProperty("学生名字")
    private String studentName;

    @ApiModelProperty("实验项目Id")
    private Integer experimentId;

    @ApiModelProperty("学生自己的分数")
    private BigDecimal studentScore;

    @ApiModelProperty("实验分数")
    private BigDecimal experimentScore;

    @ApiModelProperty("文件Url")
    private String url;


    @ApiModelProperty("作业内容")
    private String jobContent;


    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
