package com.soft.backapp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;


@Service
public final class MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mailEmail;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    public void sendForgetPasswordMail(String email, String receiverName, String code){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailEmail);
        message.setTo(email);
        message.setSubject("Forget Password");
        message.setText(
            "Dear " + receiverName +
            ", here is your password reset code: " + code
        );

        mailSender.send(message);
    }
}
