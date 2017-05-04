package com.globallogic.mykolaiv.campustrainees.simplewebapp.repository;

import com.globallogic.mykolaiv.campustrainees.simplewebapp.model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, String> {
    UserEntity findByPasswordAndUsername(String username, String password);
}