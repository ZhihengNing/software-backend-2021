package com.yuki.experiment.framework.socket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamUserInfo {

    private Integer userId;

    private Integer correctNum;

    private List<Integer> rightOrWrong=new ArrayList<>();
}
