package com.yuki.experiment.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;
import com.alibaba.fastjson.JSONWriter;
import com.yuki.experiment.common.exception.MyException;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

@Component
public class JsonUtil {

    public static JSONObject readJson(String path) {
        if (path.endsWith(".json")) {
            try {
                JSONReader reader = new JSONReader(new FileReader(path));
                return reader.readObject(JSONObject.class);
            } catch (FileNotFoundException e) {
                return null;
            }
        }
        return null;
    }

    public static boolean writeJson(String path,JSONObject object) {
        try {
            JSONWriter writer = new JSONWriter(new FileWriter(path));
            writer.writeObject(object);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
