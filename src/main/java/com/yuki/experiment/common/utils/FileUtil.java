package com.yuki.experiment.common.utils;

import com.alibaba.fastjson.JSONObject;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class FileUtil {
//    private static String PATH="C:/upload";
//
//    private final static String WEBPATH = "http://139.224.65.105:666";
    public static String generatorUrl(Object... text) {
        if (text == null) {
            return null;
        }
        StringBuilder begin = new StringBuilder(FileInfo.PATH);
        for (Object item : text) {
            begin.append(File.separator).append(item);
        }
        return String.valueOf(begin);
    }

    public static String generatorWebUrl(Object... text) {
        if (text == null) {
            return null;
        }
        StringBuilder begin = new StringBuilder(FileInfo.WEBPATH);
        for (Object item : text) {
            begin.append("/").append(item);
        }
        return String.valueOf(begin);
    }

    public static Pair<String,String> generatorTwoUrl(Object... text){
        String s = generatorUrl(text);
        String s1 = generatorWebUrl(text);
        return new Pair<>(s,s1);
    }


    public static List<JSONObject> preserveFile(List<MultipartFile> multipartFiles, String path, String webPath) {
        List<JSONObject> list = new ArrayList<>();
        for (MultipartFile item : multipartFiles) {
            JSONObject json = new JSONObject();
            if (item == null) {
                json.put("message", "文件为空,上传失败");
            } else {
                File temp = new File(path);
                if (!temp.exists()) {
                    temp.mkdirs();
                }
                String fileName = item.getOriginalFilename();
                File file = new File(path + File.separator + fileName);
                try {
                    item.transferTo(file);
                } catch (IOException e) {
                    json.put("message", fileName + "上传失败");
                }
                json.put("message", fileName + "上传成功");
                json.put("name", fileName);
                json.put("data", webPath + "/" + fileName);
                list.add(json);
            }
        }
        return list;
    }
    public static List<JSONObject> preserveFile(MultipartFile multipartFile, String path, String webPath) {
        return preserveFile(List.of(multipartFile), path, webPath);
    }


    public static boolean deleteFile(String path) {
        File file = new File(path);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }
}
