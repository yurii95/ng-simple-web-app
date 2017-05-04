package com.globallogic.mykolaiv.campustrainees.simplewebapp.controller;

import com.globallogic.mykolaiv.campustrainees.simplewebapp.exceptions.ServiceException;
import com.globallogic.mykolaiv.campustrainees.simplewebapp.model.BookEntity;
import com.globallogic.mykolaiv.campustrainees.simplewebapp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/books")
public class BookController {
    private static final String SEARCH_PARAM = "srch";
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<BookEntity> findAll() {
        return bookService.findAll();
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public List<BookEntity> findParticularBooks(@RequestParam(SEARCH_PARAM) String stringLike) {
        return bookService.findParticularBooks(stringLike);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public BookEntity addBook(@RequestBody BookEntity bookEntity) {
        return bookService.addBook(bookEntity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteBook(@PathVariable("id") String id) {
        return bookService.deleteBookById(id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public BookEntity updateBook(@RequestBody BookEntity bookEntity) {
        return bookService.updateBook(bookEntity);
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Service exception is occured")
    @ExceptionHandler({ServiceException.class})
    public void handleException() {
    }
}
