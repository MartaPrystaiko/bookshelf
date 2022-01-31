package com.learnjava.bookshelf.service;

import com.learnjava.bookshelf.dto.BookDto;
import com.learnjava.bookshelf.dto.BookPage;
import com.learnjava.bookshelf.entity.Book;

import java.util.List;

public interface BookService {
        BookPage getAllBooks(int page, int size);
//    Set<BookDto> getAllBooks();

    BookDto createBook(BookDto bookDto);

    BookDto updateBook(int id, BookDto bookDto);

    void deleteBook(int id);

    BookDto getBookDtoById(int id);

    Book getBookById(int id);

    List<BookDto> getBooksByTitle(String title);

}
