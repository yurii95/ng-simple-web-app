package com.globallogic.mykolaiv.campustrainees.poc.library.dao.repository;

import com.globallogic.mykolaiv.campustrainees.simplewebapp.configuration.ApplicationConfig;
import com.globallogic.mykolaiv.campustrainees.simplewebapp.model.UserEntity;
import com.globallogic.mykolaiv.campustrainees.simplewebapp.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class})

public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    public void testFindUserByUsernameAndPassword(){
        UserEntity userEntity = userRepository.findByPasswordAndUsername("qwerty","yurii");
        System.out.println(userEntity.toString());
    }

    @Test
    public void testFindAllUsers(){
        userRepository.findAll().forEach((user) -> System.out.println(user.toString()));

    }
}
