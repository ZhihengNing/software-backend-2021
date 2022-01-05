package com.yuki.experiment.framework.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FileInfoDTO {

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("文件web路径")
    private String fileUrl;
}
