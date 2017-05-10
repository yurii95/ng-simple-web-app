package com.globallogic.mykolaiv.campustrainees.simplewebapp.configuration;

import com.globallogic.mykolaiv.campustrainees.simplewebapp.messaging.EmailErrorHandler;
import com.globallogic.mykolaiv.campustrainees.simplewebapp.repository.BookRepository;
import com.globallogic.mykolaiv.campustrainees.simplewebapp.repository.UserRepository;
import com.mongodb.MongoClient;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.ConnectionFactory;
import java.net.UnknownHostException;

@Configuration
@EnableJms
@EnableMongoRepositories(basePackageClasses = {UserRepository.class, BookRepository.class})
public class ApplicationConfig {
    public static final String JMS_DESTINATION = "mailbox";
    private static final String DATABASE_NAME = "users";
    private static final String TYPE_ID_PROPERTY_NAME = "_type";
    public static final String CONTAINER_FACTORY = "mailFactory";

    @Bean
    public MongoClient mongoClient() throws UnknownHostException {
        return new MongoClient();
    }

    @Bean
    public MongoTemplate mongoTemplate() throws UnknownHostException {
        return new MongoTemplate(mongoClient(), DATABASE_NAME);
    }

    @Bean
    public JmsListenerContainerFactory<?> mailFactory(ConnectionFactory connectionFactory,
                                                      DefaultJmsListenerContainerFactoryConfigurer configurer,
                                                      EmailErrorHandler errorHandler) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setErrorHandler(errorHandler);
        return factory;
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName(TYPE_ID_PROPERTY_NAME);
        return converter;
    }
}


