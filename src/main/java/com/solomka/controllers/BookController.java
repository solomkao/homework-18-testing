package com.solomka.controllers;

import com.solomka.daos.BookDao;
import com.solomka.models.Book;
import com.solomka.models.CreateBookDto;
import com.solomka.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class BookController {

    private final BookDao bookDao;
    private final BookService bookService;

    public BookController(BookDao bookDao, BookService bookService) {
        this.bookDao = bookDao;
        this.bookService = bookService;
    }

    @GetMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> getAll(
            @RequestParam(value = "sortBy", defaultValue = "byName", required = false) String sortBy,
            @RequestParam(value = "limit", defaultValue = "10", required = false) String limit,
            @RequestParam(value = "offset", defaultValue = "0", required = false) String offset,
            @RequestParam(value = "name", defaultValue = "", required = false) String name,
            @RequestParam(value = "description", defaultValue = "", required = false) String description,
            @RequestParam(value = "ratingMoreThan", defaultValue = "0", required = false) String ratingMoreThan
    ) {
        return new ResponseEntity<>(bookDao.getAll(), HttpStatus.OK);
    }

    @PostMapping(
            value = "/books",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Book> createBook(@RequestBody CreateBookDto createBookDto) {
        Book newBook = new Book(
                UUID.randomUUID().toString(),
                createBookDto.getName(),
                createBookDto.getDescription(),
                createBookDto.getAuthors(),
                createBookDto.getYearOfPublication(),
                createBookDto.getNumberOfWords(),
                createBookDto.getRating());
        bookDao.addBook(newBook);
        ResponseEntity<Book> responseEntity = new ResponseEntity<>(newBook, HttpStatus.CREATED);
        return responseEntity;
    }

    @GetMapping(value = "/books/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> getById(
            @PathVariable(value = "bookId") String bookId
    ) {
        Book book = bookDao.getById(bookId);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @DeleteMapping(value = "/books/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> deleteById(
            @PathVariable(value = "bookId") String bookId
    ) {
        Book book = bookDao.deleteById(bookId);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
}
