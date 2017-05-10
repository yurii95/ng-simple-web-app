package com.globallogic.mykolaiv.campustrainees.simplewebapp.service;

import com.globallogic.mykolaiv.campustrainees.simplewebapp.exceptions.ServiceException;
import com.globallogic.mykolaiv.campustrainees.simplewebapp.model.BookEntity;
import com.globallogic.mykolaiv.campustrainees.simplewebapp.repository.BookRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional(readOnly = true)
    public List<BookEntity> findAll() throws ServiceException {
        try {
            return bookRepository.findAll();
        } catch (RuntimeException e) {
            logger.error("Exception during finding all books", e);
            throw new ServiceException("Exception during finding all books occurred", e);
        }
    }

    @Transactional(readOnly = true)
    public List<BookEntity> findByStringLike(String stringLike) throws ServiceException {
        try {
            if (StringUtils.isNotBlank(stringLike)) {
                logger.info(String.format("Search request with parameter = %s will be executed", stringLike));
                return bookRepository.findByTitleLikeOrGenreLikeOrAuthorsLike(stringLike, stringLike, stringLike);
            } else {
                logger.info("Request is contained only spaces, will be executed findAll() method");
                return findAll();
            }
        } catch (RuntimeException e) {
            logger.error(String.format("Exception during finding books with parameter: %s", stringLike), e);
            throw new ServiceException(String.format("Exception during finding books with parameter: %s", stringLike), e);
        }
    }

    public BookEntity addBook(BookEntity bookEntity) throws ServiceException {
        try {
            return bookRepository.save(bookEntity);
        } catch (RuntimeException e) {
            logger.error(String.format("Exception during adding new book: %s", bookEntity.toString()), e);
            throw new ServiceException("Exception during adding new book", e);
        }
    }

    public ResponseEntity deleteBookById(String id) throws ServiceException {
        try {
            BookEntity bookEntity = bookRepository.findOne(id);
            if (bookEntity == null) {
                logger.info(String.format("Unable to delete. Book with id %s not found", id));
                return new ResponseEntity<BookEntity>(HttpStatus.NOT_FOUND);
            }
            bookRepository.delete(id);
            logger.info(String.format("Book with id %s not found was deleted successful", id));
            return new ResponseEntity<BookEntity>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            logger.error(String.format("Exception during deleting book with id: %s", id), e);
            throw new ServiceException(String.format("Exception during deleting book with id: %s", id), e);
        }
    }

    public BookEntity updateBook(BookEntity bookEntity) throws ServiceException {
        try {
            return bookRepository.save(bookEntity);
        } catch (RuntimeException e) {
            logger.error(String.format("Exception during updating book: %s", bookEntity.toString()), e);
            throw new ServiceException(String.format("Exception during updating book with id: %s", bookEntity.getIdBook()), e);
        }
    }
}