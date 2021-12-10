package com.yuki.experiment.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.yuki.experiment.common.exception.FileIsNullException;
import com.yuki.experiment.framework.dto.FileInfo;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class FileUtil {
//    private static String PATH="C:/upload";
//
//    private final static String WEBPATH = "http://139.224.65.105:666";
    public static String generatorUrl(Object... text) {
        if (text == null) {
            return null;
        }
        StringBuilder begin = new StringBuilder(UrlInfo.PATH);
        for (Object item : text) {
            begin.append(File.separator).append(item);
        }
        return String.valueOf(begin);
    }

    public static String generatorWebUrl(Object... text) {
        if (text == null) {
            return null;
        }
        StringBuilder begin = new StringBuilder(UrlInfo.WEBPATH);
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

    public static void preserveMyFile(List<MultipartFile> multipartFiles) {
        for (MultipartFile item : multipartFiles) {
            if (item != null) {
                File file=new File("\\\\139.224.65.105:666\\course\\60020\\摩尔庄园.pptx");
                //File file = new File(filePaths + "/" + item.getOriginalFilename());
                try {
                    item.transferTo(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static List<FileInfo> preserveFile(List<MultipartFile> multipartFiles, String path, String webPath) {
        List<FileInfo> list = new ArrayList<>();
        for (MultipartFile item : multipartFiles) {
            FileInfo fileInfo=new FileInfo();
            if (item != null) {
                File temp = new File(path);
                if (!temp.exists()) {
                    temp.mkdirs();
                }
                String fileName = item.getOriginalFilename();
                File file = new File(path + File.separator + fileName);
                try {
                    item.transferTo(file);
                } catch (IOException e) {
                    log.info("message" + fileName + "上传失败");
                    throw new FileIsNullException();
                }
                fileInfo.setFileName(fileName);
                fileInfo.setFileUrl(webPath+"/"+fileName);
                list.add(fileInfo);
            }
        }
        return list;
    }
    public static FileInfo preserveFile(MultipartFile multipartFile, String path, String webPath) {
        return preserveFile(Collections.singletonList(multipartFile), path, webPath).get(0);
    }


    public static void deleteFile(String path) {
        String replace = path.replace(UrlInfo.WEBPATH, UrlInfo.PATH);
        File file = new File(replace);
        if (file.isFile() && file.exists()) {
            file.delete();
        }
    }
}
