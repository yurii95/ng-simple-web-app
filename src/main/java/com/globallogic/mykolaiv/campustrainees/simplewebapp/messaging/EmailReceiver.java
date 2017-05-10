package com.globallogic.mykolaiv.campustrainees.simplewebapp.messaging;

import com.globallogic.mykolaiv.campustrainees.simplewebapp.configuration.ApplicationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class EmailReceiver {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private NotificationService notificationService;

    @Autowired
    public EmailReceiver(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @JmsListener(destination = ApplicationConfig.JMS_DESTINATION, containerFactory = ApplicationConfig.CONTAINER_FACTORY)
    public void receiveMessage(Email email) {
        logger.info("Received <" + email + ">");
        notificationService.sendNotification(email);
        logger.info(String.format("Email to %s was successful sent", email.getTo()));
    }
}
