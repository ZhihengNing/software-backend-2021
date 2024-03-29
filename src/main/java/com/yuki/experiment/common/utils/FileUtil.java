package com.yuki.experiment.common.utils;

import com.yuki.experiment.common.exception.FileIsNullException;
import com.yuki.experiment.framework.dto.FileInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class FileUtil {
    private static String PATH;

    private static String WEBPATH;

    private static final RestTemplate restTemplate = new RestTemplate();

    @Value("${file.path}")
    public void setPATH(String PATH) {
        FileUtil.PATH = PATH;
    }

    @Value("${file.webPath}")
    public void setWEBPATH(String WEBPATH) {
        FileUtil.WEBPATH = WEBPATH;
    }

    public static String getPATH() {
        return PATH;
    }

    public static String getWEBPATH() {
        return WEBPATH;
    }

    public static String generatorUrl(Object... text) {
        if (text == null) {
            return null;
        }
        StringBuilder begin = new StringBuilder(PATH);
        for (Object item : text) {
            begin.append(File.separator).append(item);
        }
        return String.valueOf(begin);
    }

    public static String generatorWebUrl(Object... text) {
        if (text == null) {
            return null;
        }
        StringBuilder begin = new StringBuilder(WEBPATH);
        for (Object item : text) {
            begin.append("/").append(item);
        }
        return String.valueOf(begin);
    }

    public static Pair<String, String> generatorTwoUrl(Object... text) {
        String s = generatorUrl(text);
        String s1 = generatorWebUrl(text);
        return new Pair<>(s, s1);
    }

    public static void preserveMyFile(List<MultipartFile> multipartFiles) {
        for (MultipartFile item : multipartFiles) {
            if (item != null) {
                File file = new File("\\\\139.224.65.105:666\\course\\60020\\摩尔庄园.pptx");
                //File file = new File(filePaths + "/" + item.getOriginalFilename());
                try {
                    item.transferTo(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static List<FileInfoDTO> preserveFile(List<MultipartFile> multipartFiles, String path, String webPath) {
        List<FileInfoDTO> list = new ArrayList<>();
        for (MultipartFile item : multipartFiles) {
            FileInfoDTO fileInfoDTO = new FileInfoDTO();
            if (!EmptyUtil.isEmpty(item)) {
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
                fileInfoDTO.setFileName(fileName);
                fileInfoDTO.setFileUrl(webPath + "/" + fileName);
                list.add(fileInfoDTO);
            }
        }
        return list;
    }

    public static FileInfoDTO preserveFile(MultipartFile multipartFile, String path, String webPath) {
        List<FileInfoDTO> fileInfoDTOS = preserveFile(Collections.singletonList(multipartFile), path, webPath);
        if(!EmptyUtil.isEmpty(fileInfoDTOS)){
            return  fileInfoDTOS.get(0);
        }
        return null;
    }


    public static void deleteFile(String webUrl) {
        if (webUrl != null) {
            String replace = webUrl.replace(WEBPATH, PATH);
            File file = new File(replace);
            if (file.isFile() && file.exists()) {
                file.delete();
            }
        }
    }

    public static void downloadFile(String url, HttpServletResponse response) {
        String localPath = url.replace(WEBPATH, PATH).replace("/", "\\").trim();
        log.info("localPath:" + localPath);
        byte[] buffer = new byte[1024];
        try (FileInputStream fis = new FileInputStream(localPath);
             BufferedInputStream bis = new BufferedInputStream(fis)) {

            OutputStream os = response.getOutputStream();

            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        } catch (IOException ignored) {
        }
    }

    public static void downloadAllFile(String url, HttpServletResponse response) {
        Resource forObject1 = restTemplate.getForObject(url.trim(), Resource.class);
        try {
            InputStream inputStream = forObject1.getInputStream();
            byte[] buffer = new byte[1024];
            try (BufferedInputStream bis = new BufferedInputStream(inputStream)) {

                OutputStream os = response.getOutputStream();

                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
