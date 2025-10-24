package com.benjamin.crypto_matrix.user.service;

import java.util.Map;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.benjamin.crypto_matrix.utils.ResponseUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SendEmailService {
    private final JavaMailSender mailSender;

    public Map<String, Object> sendEmail(String to, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("Crypto Matrix <benjaminowolab@gmail.com>");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);

        return ResponseUtil.createSuccessResponse("Email sent successfully");
   }
}