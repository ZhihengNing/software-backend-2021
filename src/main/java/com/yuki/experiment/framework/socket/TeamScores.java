package com.yuki.experiment.framework.socket;

import lombok.Data;

@Data
public class TeamScores {

    private  String teamId;

    private Integer userId;

    private Integer correctNum;
}
