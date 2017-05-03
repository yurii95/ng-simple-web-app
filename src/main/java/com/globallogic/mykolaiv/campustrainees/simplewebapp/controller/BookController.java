package com.globallogic.mykolaiv.campustrainees.simplewebapp.controller;

import com.globallogic.mykolaiv.campustrainees.simplewebapp.model.BookEntity;
import com.globallogic.mykolaiv.campustrainees.simplewebapp.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/books")
public class BookController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<BookEntity> findAll() {
        return bookRepository.findAll();
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public List<BookEntity> filterBooks(@RequestParam("srch") String stringLike) {
        return bookRepository.findByTitleLikeOrGenreLikeOrAuthorsLike(stringLike, stringLike, stringLike);
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public BookEntity addBook(@RequestBody BookEntity bookEntity) {
        System.out.println(bookEntity.toString());
        return bookRepository.save(bookEntity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<BookEntity> deleteBook(@PathVariable("id") String id) {
        BookEntity bookEntity = bookRepository.findOne(id);
        if (bookEntity == null) {
            logger.info(String.format("Unable to delete. Book with id %s not found", id));
            return new ResponseEntity<BookEntity>(HttpStatus.NOT_FOUND);
        }
        bookRepository.delete(id);
        logger.info(String.format("Book with id %s not found was deleted successful", id));
        return new ResponseEntity<BookEntity>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<BookEntity> updateBook(@PathVariable("id") String id, @RequestBody BookEntity bookEntity) {

        BookEntity bookEntity1 = bookRepository.findOne(id);

        if (bookEntity1 == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<BookEntity>(HttpStatus.NOT_FOUND);
        }
        bookEntity1 = bookEntity;

        bookRepository.save(bookEntity1);
        return new ResponseEntity<BookEntity>(bookEntity1, HttpStatus.OK);
    }
}
