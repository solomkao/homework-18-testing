package com.solomka.services;

import com.solomka.daos.BookDao;
import com.solomka.exceptions.BadIdException;
import com.solomka.exceptions.BookNameIsNullException;
import com.solomka.models.Book;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    private final BookService bookService;

    @Mock
    private BookDao bookDao = new BookDao();

    public BookServiceTest() {
        bookService = new BookService(bookDao);
    }

    @BeforeAll
    void setUp() {
        bookDao = Mockito.mock(BookDao.class);
    }

    @Test
    void getBookByIdSuccessTest() {
        String bookId = "book-id";

        Mockito.when(bookDao.getById(bookId)).thenReturn(new Book(bookId));

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
}
