package com.yuki.experiment.common.utils;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class UrlUtil {

    private static final String FILE_PATH="C:"+File.separator+"upload";

    public static String generatorUrl(Object...text){
        if(text==null){
            return null;
        }
        StringBuilder begin = new StringBuilder(FILE_PATH);
        for(Object item:text){
            begin.append(File.separator).append(item);
        }
        return String.valueOf(begin);
    }
}
