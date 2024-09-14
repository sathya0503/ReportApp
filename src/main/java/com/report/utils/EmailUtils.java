package com.report.utils;

import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;


@Component
public class EmailUtils {

    @Autowired
    private JavaMailSender mailSender;

    public boolean sendMail(String subject, String body, String to, File f) {

        try {

            // MimeMessage mimeMessage = mailSender.createMimeMessage();

            // MimeMessageHelper helper =new MimeMessageHelper(mimeMessage);

            // helper.setSubject(subject);
            // helper.setText(body, true);
            // helper.setTo(to);
            // helper.addAttachment("Plans-Info.xlsx", f);
            // mailSender.send(mimeMessage);

            // Creating a mime message
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper;

            // Setting multipart as true for attachments to
            // be send
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setText(body, true);
            mimeMessageHelper.addAttachment("PlanInfo", f);

            mailSender.send(mimeMessage);

            // SimpleMailMessage message = new SimpleMailMessage();
            // message.setTo(to);
            // message.setSubject(subject);
            // message.setText(body);
            // mailSender.send(message);

        } catch (Exception e) {
            // TODO: handle exception
        }
        return true;
    }
}
