package com.Estructura.API.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@AllArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    @Async
    public void send(String receiver, String mail, String subject,
        String sender) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message       = mailSender.createMimeMessage();
        var         messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("horizonstruevent@gmail.com", sender);
        messageHelper.setTo(receiver);
        messageHelper.setSubject(subject);
        messageHelper.setText(mail, true);
        mailSender.send(message);
    }
}
