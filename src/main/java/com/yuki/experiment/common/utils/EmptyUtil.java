package com.yuki.experiment.common.utils;

import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public class EmptyUtil {

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }

    public static boolean isEmpty(String string) {

        //return StringUtils.isBlank(string); actually it is bad writting
        return string == null || string.length() == 0 || "".equals(string.trim());
    }
    public static boolean isEmpty(MultipartFile file){
        return file==null||file.getSize()==0;
    }
}
