package com.yuki.experiment.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;
import com.yuki.experiment.common.exception.MyException;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

@Component
public class JsonUtil {
    private JSONObject readJson(String path)throws MyException {
        if(path.endsWith(".json")) {
            try {
                JSONReader reader = new JSONReader(new FileReader(path));
                return reader.readObject(JSONObject.class);
            } catch (FileNotFoundException e) {
                throw new MyException("file is not be found");
            }
        }
        throw new MyException("can't solve other types of files except .json");
    }


}
