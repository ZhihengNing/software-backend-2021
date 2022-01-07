package com.yuki.experiment.framework.socket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamScores {

    private  String teamId;

    private Integer userId;

    private Integer correctNum;
}
