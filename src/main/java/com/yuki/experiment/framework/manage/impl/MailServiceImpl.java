package com.yuki.experiment.framework.manage.impl;

import com.yuki.experiment.framework.manage.MailService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class MailServiceImpl implements MailService {

    private static String username;

    //下面一定要用注入的方式2333
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    public void setUsername(String user){
        username=user;
    }

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender){
        this.mailSender=mailSender;
    }

    @Override
    public boolean mailSend(String addressee,
                            String subject,
                            String text,
                            MultipartFile[]files) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(username);
        helper.setTo(addressee);
        helper.setSubject(subject);
        helper.setText(text);
        for (MultipartFile item : files) {
            if (item == null) {
                return false;
            }
            String name = item.getOriginalFilename();
            File file = new File("C:/temp/" + name);
            try {
                item.transferTo(file);
                helper.addAttachment(name, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mailSender.send(helper.getMimeMessage());
        return true;
    }

    @Override
    public boolean mailSend(String addressee,
                            String subject,
                            String text) {
        //创建邮件内容
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);//这里指的是发送者的账号
        message.setTo(addressee);
        message.setSubject(subject);//邮件标题
        message.setText(text);//邮件内容
        //发送邮件
        mailSender.send(message);
        log.info("\033[32;1m" + "发送给 " + addressee + " 的邮件发送成功" + "\033[0m");
        return true;
    }

}
