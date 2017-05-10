package com.globallogic.mykolaiv.campustrainees.simplewebapp.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private static final String EMAIL_PROVIDER_ADDRESS = "kozak9995@gmail.com";
    private JavaMailSender javaMailSender;

    @Autowired
    public NotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    void sendNotification(Email email) throws MailException {
        MimeMessagePreparator emailMessage = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(email.getTo());
            messageHelper.setFrom(EMAIL_PROVIDER_ADDRESS);
            messageHelper.setSubject(email.getSubject());
            messageHelper.setText(email.getBody());
        };
        javaMailSender.send(emailMessage);
    }
}
