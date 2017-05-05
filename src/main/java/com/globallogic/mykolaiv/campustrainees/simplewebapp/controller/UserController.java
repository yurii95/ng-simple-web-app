package com.globallogic.mykolaiv.campustrainees.simplewebapp.controller;

import com.globallogic.mykolaiv.campustrainees.simplewebapp.exceptions.ServiceException;
import com.globallogic.mykolaiv.campustrainees.simplewebapp.model.UserEntity;
import com.globallogic.mykolaiv.campustrainees.simplewebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public boolean authenticateUser(@RequestBody UserEntity userEntity) {
        return userService.authenticationUser(userEntity);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseEntity<UserEntity> insertUser(@RequestBody UserEntity userEntity) {
        return userService.createUser(userEntity);
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Service exception is occurred")
    @ExceptionHandler({ServiceException.class})
    public void handleException() {
    }
}
