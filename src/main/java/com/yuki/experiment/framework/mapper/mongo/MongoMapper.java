package com.yuki.experiment.framework.mapper.mongo;


import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Repository;

@Repository()
public interface MongoMapper {

    JSONObject getPractice();
}
