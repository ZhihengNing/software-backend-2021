package com.yuki.experiment.framework.manage;

import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;

public interface MailService {
    boolean mailSend(String addressee, String subject, String text, MultipartFile[]files) throws MessagingException;

    boolean mailSend(String addressee, String subject, String text);
}
