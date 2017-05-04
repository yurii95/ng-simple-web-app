package com.globallogic.mykolaiv.campustrainees.simplewebapp.repository;

import com.globallogic.mykolaiv.campustrainees.simplewebapp.model.BookEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookRepository extends MongoRepository<BookEntity, String> {
    List<BookEntity> findByTitleLikeOrGenreLikeOrAuthorsLike(String stringLike, String stringLike1, String stringLike2);
}