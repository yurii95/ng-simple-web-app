package com.globallogic.mykolaiv.campustrainees.simplewebapp.service;

import com.globallogic.mykolaiv.campustrainees.simplewebapp.exceptions.ServiceException;
import com.globallogic.mykolaiv.campustrainees.simplewebapp.model.BookEntity;
import com.globallogic.mykolaiv.campustrainees.simplewebapp.repository.BookRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {
    private static final String STRING_LIKE = "Potter";
    private static final String ID_BOOK = "qwerty";

    @Mock
    private BookRepository bookRepository;

    private BookService bookService;
    private BookEntity testBook;
    private List<BookEntity> listOfBooks;

    @Before
    public void setUpMocks() throws Exception {
        testBook = new BookEntity();
        testBook.setTitle("title");
        testBook.setAuthors("Chris");
        testBook.setGenre("fantasy");
        testBook.setPages("155");
        testBook.setDescription("description");
        bookService = new BookService(bookRepository);
    }

    private void setUpListOfBooks() {
        listOfBooks = new ArrayList<>();
        BookEntity book1 = new BookEntity();
        book1.setTitle("title");
        book1.setAuthors("Chris");
        book1.setGenre("fantasy");
        book1.setPages("155");
        book1.setDescription("description");

        BookEntity book2 = new BookEntity();
        book1.setTitle("title2");
        book1.setAuthors("Chris2");
        book1.setGenre("fantasy2");
        book1.setPages("1552");
        book1.setDescription("description2");

        listOfBooks.add(book1);
        listOfBooks.add(book2);
    }

    @Test
    public void testCreate_expectedSaveMethodIsInvoked() throws ServiceException {
        when(bookRepository.save(testBook)).thenReturn(testBook);
        BookEntity savedBook = bookService.addBook(testBook);
        verify(bookRepository).save(testBook);
        assertThat(testBook, is(equalTo(savedBook)));
    }

    @Test(expected = ServiceException.class)
    public void testCreate_expectedServiceException() throws ServiceException {
        testBook.setGenre(null);
        when(bookRepository.save(testBook)).thenThrow(new RuntimeException());
        bookService.addBook(testBook);
    }

    @Test
    public void testFindAll_expectedFindAllMethodIsInvoked() throws ServiceException {
        setUpListOfBooks();
        when(bookRepository.findAll()).thenReturn(listOfBooks);
        listOfBooks = bookService.findAll();
        verify(bookRepository).findAll();
        assertThat(listOfBooks.size(), is(equalTo(2)));
    }

    @Test
    public void testFindByStringLike_expectedEmptyList() throws ServiceException {
        setUpListOfBooks();
        when(bookRepository.findByTitleLikeOrGenreLikeOrAuthorsLike(STRING_LIKE, STRING_LIKE, STRING_LIKE)).thenReturn(new ArrayList<>());
        listOfBooks = bookService.findByStringLike(STRING_LIKE);
        verify(bookRepository).findByTitleLikeOrGenreLikeOrAuthorsLike(STRING_LIKE, STRING_LIKE, STRING_LIKE);
        assertThat(listOfBooks.size(), is(equalTo(0)));
    }

    @Test
    public void testFindByEmptyString_expectedFindAllMethodInvoked() throws ServiceException {
        setUpListOfBooks();
        when(bookRepository.findAll()).thenReturn(listOfBooks);
        listOfBooks = bookService.findByStringLike("");
        verify(bookRepository).findAll();
        assertThat(listOfBooks.size(), is(equalTo(2)));
    }

    @Test
    public void testUpdate_expectedSaveMethodIsInvoked() throws ServiceException {
        when(bookRepository.save(testBook)).thenReturn(testBook);
        BookEntity updatedBook = bookService.updateBook(testBook);
        assertThat("Books are not equal", testBook, is(equalTo(updatedBook)));
        verify(bookRepository).save(testBook);
    }

    @Test(expected = ServiceException.class)
    public void testUpdate_expectedServiceExceptionIfNullArgumentPassed() throws ServiceException {
        doThrow(RuntimeException.class).when(bookRepository).save(testBook);
        bookService.updateBook(testBook);
    }

    @Test
    public void testDelete_expectedBookIsNotFound() throws ServiceException {
        when(bookRepository.findOne(ID_BOOK)).thenReturn(null);
        ResponseEntity responseEntity = bookService.deleteBookById(ID_BOOK);
        verify(bookRepository).findOne(ID_BOOK);
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.NOT_FOUND)));
    }

    @Test
    public void testDelete_expectedDeleteIsInvoked() throws ServiceException {
        when(bookRepository.findOne(ID_BOOK)).thenReturn(testBook);
        doNothing().when(bookRepository).delete(ID_BOOK);
        ResponseEntity responseEntity = bookService.deleteBookById(ID_BOOK);
        verify(bookRepository).findOne(ID_BOOK);
        verify(bookRepository).delete(ID_BOOK);
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.NO_CONTENT)));
    }
}