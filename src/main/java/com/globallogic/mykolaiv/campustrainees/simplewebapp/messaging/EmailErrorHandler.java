package com.globallogic.mykolaiv.campustrainees.simplewebapp.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;

@Service
public class EmailErrorHandler implements ErrorHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void handleError(Throwable t) {
        logger.error("Exception during sending email", t.getMessage());
    }
}