package com.solomka.services;

import com.solomka.daos.BookDao;
import com.solomka.exceptions.BadIdException;
import com.solomka.exceptions.BookNameIsNullException;
import com.solomka.exceptions.BookNameIsTooLongException;
import com.solomka.models.Book;
import com.solomka.models.CreateBookDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookService {

    public final BookDao bookDao;

    public BookService(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public Book getById(final String bookId) {
        if (bookId == null || bookId.isBlank()) {
            throw new BadIdException();
        }
        return bookDao.getById(bookId);
    }

    public Book createBook(final CreateBookDto createBookDto) {
        final Book newBook = new Book(UUID.randomUUID().toString());
        newBook.setName(getValidatedBookName(createBookDto.getName()));
        newBook.setDescription(createBookDto.getDescription());
        newBook.setAuthors(createBookDto.getAuthors());
        newBook.setNumberOfWords(createBookDto.getNumberOfWords());
        newBook.setRating(createBookDto.getRating());
        newBook.setYearOfPublication(createBookDto.getYearOfPublication());

        return bookDao.addBook(newBook);
    }

    public String getValidatedBookName(final String name) {
        if (name == null) {
            throw new BookNameIsNullException();
        }
        if (name.length() > 1000) {
            throw new BookNameIsTooLongException();
        }
        return name.trim();
    }
}
