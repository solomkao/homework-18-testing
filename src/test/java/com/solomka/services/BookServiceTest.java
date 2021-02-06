package com.solomka.services;

import com.solomka.daos.BookDao;
import com.solomka.exceptions.BadIdException;
import com.solomka.exceptions.BookNameIsNullException;
import com.solomka.models.Book;
import com.solomka.models.CreateBookDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
        when(this.bookDao.getById(bookId)).thenReturn(new Book(bookId));
        Book bookFromDB = this.bookService.getById(bookId);
        assertEquals(
                bookId,
                bookFromDB.getBookId()
        );
    }

    @Test
    void getBookByIdBadIdExceptionTest() {
        assertThrows(
                BadIdException.class,
                () -> this.bookService.getById("       ")
        );
    }

    @Test
    void getValidatedBookNameExpectBookNameIsNullExceptionTest() {
        assertThrows(
                BookNameIsNullException.class,
                () -> this.bookService.getValidatedBookName(null)
        );
    }

    @Test
    void deleteBookByIdSuccessfulTest() {
        String bookId = "book_id";
        Book book = new Book();
        book.setBookId(bookId);
        when(this.bookDao.deleteById(bookId)).thenReturn(book);
        Book bookDeleted = this.bookService.deleteBookById(bookId);
        assertEquals(
                book,
                bookDeleted
        );
    }

    @Test
    void deleteBookByIdFailedTest() {
        String bookId = "book_id";
        Book book = new Book();
        book.setBookId(bookId);
        when(this.bookDao.deleteById(bookId)).thenReturn(new Book("another_book_id"));
        Book bookDeleted = this.bookService.deleteBookById(bookId);
        assertNotEquals(
                book,
                bookDeleted
        );
    }

    @Test
    void createBookTest(){
        String bookId="book id";
        CreateBookDto createBookDto = new CreateBookDto();
        createBookDto.setName("Book #1");
        createBookDto.setDescription("Description");
        createBookDto.setAuthors(List.of("John Brien"));
        createBookDto.setNumberOfWords(1000);
        createBookDto.setRating(10);
        createBookDto.setYearOfPublication(1999);
        Book newBook = new Book();
        newBook.setBookId(bookId);
        newBook.setName(createBookDto.getName());
        newBook.setDescription(createBookDto.getDescription());
        newBook.setAuthors(createBookDto.getAuthors());
        newBook.setNumberOfWords(createBookDto.getNumberOfWords());
        newBook.setRating(createBookDto.getRating());
        newBook.setYearOfPublication(createBookDto.getYearOfPublication());
        when(bookDao.addBook(any(Book.class))).thenReturn(newBook);
        Book bookCreated = bookService.createBook(createBookDto);
        assertEquals(
                newBook,
                bookCreated
        );
    }
}
