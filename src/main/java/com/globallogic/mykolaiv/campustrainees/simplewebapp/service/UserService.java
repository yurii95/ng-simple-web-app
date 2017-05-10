package com.globallogic.mykolaiv.campustrainees.simplewebapp.service;

import com.globallogic.mykolaiv.campustrainees.simplewebapp.configuration.ApplicationConfig;
import com.globallogic.mykolaiv.campustrainees.simplewebapp.exceptions.ServiceException;
import com.globallogic.mykolaiv.campustrainees.simplewebapp.messaging.Email;
import com.globallogic.mykolaiv.campustrainees.simplewebapp.model.UserEntity;
import com.globallogic.mykolaiv.campustrainees.simplewebapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String EMAIL_MESSAGE = "Congratulations!!! You were successful registered";
    private static final String EMAIL_SUBJECT = "Registration";
    private final UserRepository userRepository;
    private final JmsTemplate jmsTemplate;

    @Autowired
    public UserService(UserRepository userRepository, JmsTemplate jmsTemplate) {
        this.userRepository = userRepository;
        this.jmsTemplate = jmsTemplate;
    }

    public ResponseEntity<UserEntity> createUser(UserEntity userEntity) throws ServiceException {
        try {
            UserEntity existedUser = userRepository.findByUsername(userEntity.getUsername());
            if (existedUser == null) {
                UserEntity createdUser = userRepository.insert(userEntity);
                logger.info(String.format("New user with login %s is successful created", userEntity.getUsername()));
                jmsTemplate.convertAndSend(ApplicationConfig.JMS_DESTINATION, new Email(userEntity.getEmail(), EMAIL_SUBJECT, EMAIL_MESSAGE));
                return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
            } else {
                logger.info(String.format("User with username: %s already exist", userEntity.getUsername()));
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (MailException e) {
            System.out.println(e.getMessage());
            throw new ServiceException("Exception during creating user", e);
        } catch (RuntimeException e) {
            logger.error("Exception during creating user", e);
            throw new ServiceException("Exception during creating user", e);
        }
    }

    @Transactional(readOnly = true)
    public boolean authenticationUser(UserEntity userEntity) throws ServiceException {
        try {
            UserEntity authenticatedUser = userRepository.findByPasswordAndUsername(userEntity.getPassword(), userEntity.getUsername());
            return authenticatedUser != null;
        } catch (RuntimeException e) {
            logger.error(String.format("Exception during creating user %s", userEntity.getUsername()), e);
            throw new ServiceException(String.format("Exception during creating user %s", userEntity.getUsername()), e);
        }
    }
}