package com.solomka.services;

import com.solomka.daos.BookDao;
import com.solomka.exceptions.BadIdException;
import com.solomka.exceptions.BookNameIsNullException;
import com.solomka.models.Book;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookServiceTest {
    @InjectMocks
    private BookService bookService;
    @Mock
    private BookDao bookDao;

    @Test
    void getBookByIdSuccessTest() {
        String bookId = "book_id";
        when(bookDao.getById(bookId)).thenReturn(new Book(bookId));
        Book bookFromDB = bookService.getById(bookId);
        assertEquals(
                bookId,
                bookFromDB.getBookId()
        );
    }

    @Test
    void getBookByIdBadIdExceptionTest() {
        assertThrows(
                BadIdException.class,
                () -> bookService.getById("       ")
        );
    }

    @Test
    void getValidatedBookNameExpectBookNameIsNullExceptionTest() {
        assertThrows(
                BookNameIsNullException.class,
                () -> bookService.getValidatedBookName(null)
        );
    }

    @Test
    void deleteBookByIdSuccessfulTest(){
        String bookId ="book_id";
        Book book = new Book();
        book.setBookId(bookId);
        when(bookDao.deleteById(bookId)).thenReturn(book);
        Book bookDeleted = bookService.deleteBookById(bookId);
        assertEquals(
                book,
                bookDeleted
        );
    }

    @Test
    void deleteBookByIdFailedTest(){
        String bookId ="book_id";
        Book book = new Book();
        book.setBookId(bookId);
        when(bookDao.deleteById(bookId)).thenReturn(new Book("another_book_id"));
        Book bookDeleted = bookService.deleteBookById(bookId);
        assertNotEquals(
                book,
                bookDeleted
        );
    }
}
