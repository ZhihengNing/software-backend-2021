package com.yuki.experiment.framework.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class SocketMessageDTO {

    private Integer userId;

    private JSONObject message;
}
