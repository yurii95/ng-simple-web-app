package com.globallogic.mykolaiv.campustrainees.simplewebapp.configuration;

import com.globallogic.mykolaiv.campustrainees.simplewebapp.repository.BookRepository;
import com.globallogic.mykolaiv.campustrainees.simplewebapp.repository.UserRepository;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.UnknownHostException;

@Configuration
@EnableMongoRepositories(basePackageClasses = {UserRepository.class, BookRepository.class})
public class ApplicationConfig {
    @Bean
    public MongoClient mongoClient() throws UnknownHostException {
        return new MongoClient();
    }

    @Bean
    public MongoTemplate mongoTemplate() throws UnknownHostException {
        return new MongoTemplate(mongoClient(), "users");
    }
}


