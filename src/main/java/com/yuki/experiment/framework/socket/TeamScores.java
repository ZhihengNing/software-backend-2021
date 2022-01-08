package com.yuki.experiment.framework.socket;

import com.yuki.experiment.framework.entity.Practice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamScores {

    private String teamId;

    private Practice practice;

    private List<TeamUserInfo>teamUserInfos;

}
