package com.yuki.experiment.common.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileInfo {
    public static String PATH;

    public static String WEBPATH;

    @Value("${file.path}")
    public void setPATH(String PATH) {
        FileInfo.PATH = PATH;
    }
    @Value("${file.webPath}")
    public void setWEBPATH(String WEBPATH) {
        FileInfo.WEBPATH = WEBPATH;
    }
}
