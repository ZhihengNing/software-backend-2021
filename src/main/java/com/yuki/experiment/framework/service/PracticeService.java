package com.yuki.experiment.framework.service;

import com.yuki.experiment.framework.entity.Practice;

import java.util.List;

public interface PracticeService {

    List<Practice> getAll();

    void insert(Practice jsonObject);

    void insert();
}
