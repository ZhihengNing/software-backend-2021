package com.yuki.experiment.common.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UrlInfo {
    public static String PATH;

    public static String WEBPATH;

    @Value("${file.path}")
    public void setPATH(String PATH) {
        UrlInfo.PATH = PATH;
    }
    @Value("${file.webPath}")
    public void setWEBPATH(String WEBPATH) {
        UrlInfo.WEBPATH = WEBPATH;
    }
}
