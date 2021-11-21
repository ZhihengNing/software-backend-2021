package com.yuki.experiment.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    private final static String PATH="C:"+File.separator+"upload";

    public static String generatorUrl(Object...text) {
        if (text == null) {
            return null;
        }
        StringBuilder begin = new StringBuilder(PATH);
        for (Object item : text) {
            begin.append(File.separator).append(item);
        }
        return String.valueOf(begin);
    }

    public static List<JSONObject> preserveFile(MultipartFile[] multipartFiles, String path) {
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
                String path1 = path + File.separator + fileName;
                File file = new File(path1);
                try {
                    item.transferTo(file);
                } catch (IOException e) {
                    json.put("message", fileName + "上传失败");
                }
                json.put("message", fileName + "上传成功");
                json.put("name", fileName);
                json.put("data", path1);
                list.add(json);
            }
        }
        return list;
    }

    public static boolean deleteFile(String path) {
        File file = new File(path);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }
}
