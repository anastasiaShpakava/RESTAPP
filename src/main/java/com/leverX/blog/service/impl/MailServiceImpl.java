package com.leverX.blog.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String login;

    public void send(String emailTo, String subject,  String message){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(login);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }
}
