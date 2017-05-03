package com.globallogic.mykolaiv.campustrainees.simplewebapp.controller;

import com.globallogic.mykolaiv.campustrainees.simplewebapp.model.UserEntity;
import com.globallogic.mykolaiv.campustrainees.simplewebapp.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = Logger.getLogger(UserController.class);
//
@Autowired
private UserRepository userRepository;


    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public UserEntity findAll(){
        UserEntity userEntity =new UserEntity();
        userEntity.setPassword("asdsd");
        userEntity.setUsername("sdafasd");
        return userEntity;
    }

    @CrossOrigin
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public boolean findById(@RequestBody UserEntity userEntity){
        System.out.println(userEntity.getUsername());
        System.out.println(userEntity.getPassword());
        UserEntity userEntity1 = userRepository.findByPasswordAndUsername(userEntity.getPassword(),userEntity.getUsername());
        if(userEntity1 != null){
            return true;
        }
        return false;
    }
//
//    @RequestMapping(value = "/test", method = RequestMethod.GET)
//    public ResponseEntity<?> add(@RequestBody UserEntity userEntity){
//         logger.debug("UserEmail: " + userEntity.getUserEmail());
//         UserEntity createdEntity = userRepository.create(userEntity);
//         URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdEntity.getUserId()).toUri();
//         logger.debug("Location: " + location.toString());
//         return ResponseEntity.created(location).build();
//    }
//@RequestParam String username, @RequestParam String password
}
