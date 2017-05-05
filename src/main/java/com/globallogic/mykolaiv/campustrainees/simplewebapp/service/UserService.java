package com.globallogic.mykolaiv.campustrainees.simplewebapp.service;

import com.globallogic.mykolaiv.campustrainees.simplewebapp.exceptions.ServiceException;
import com.globallogic.mykolaiv.campustrainees.simplewebapp.model.UserEntity;
import com.globallogic.mykolaiv.campustrainees.simplewebapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<UserEntity> createUser(UserEntity userEntity) throws ServiceException {
        try {
            UserEntity existedUser = userRepository.findByUsername(userEntity.getUsername());
            if (existedUser == null) {
                UserEntity createdUser = userRepository.insert(userEntity);
                logger.info("New user is successful created");
                return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
            } else {
                logger.info(String.format("User with username: %s already exist", userEntity.getUsername()));
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
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