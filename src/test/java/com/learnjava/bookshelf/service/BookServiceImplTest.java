package com.learnjava.bookshelf.service;

import com.learnjava.bookshelf.dao.BookDao;
import com.learnjava.bookshelf.dto.BookDto;
import com.learnjava.bookshelf.dto.BookPage;
import com.learnjava.bookshelf.entity.Author;
import com.learnjava.bookshelf.entity.Book;
import com.learnjava.bookshelf.entity.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mapstruct.ap.internal.util.Collections.asSet;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookDao bookDao;
    @Mock
    private CategoryService categoryService;
    @Mock
    private AuthorService authorService;
    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void givenPageAndSizeWhenGettinAllBooksThenReturnBooks() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Tails1", generateCategory(1), 123.5, generateAuthors(1)));
        books.add(new Book(2, "Tails2", generateCategory(1), 124.5, generateAuthors(1)));
        final PageImpl<Book> bookPage = new PageImpl<>(books);
        Mockito.when(bookDao.myFindAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(bookPage);

        List<BookDto> bookDtos = new ArrayList<>();
        final BookDto bookDto1 = new BookDto(1, "Tails1", asSet(1), 123.5, asList(1));
        final BookDto bookDto2 = new BookDto(2, "Tails2", asSet(1), 124.5, asList(1));
        bookDtos.add(bookDto1);
        bookDtos.add(bookDto2);
        final BookPage expectedResult = new BookPage();
        expectedResult.setBooks(bookDtos);
        expectedResult.setTotalElements(2);

        final BookPage actualResult = bookService.getAllBooks(0, 2);

        Assertions.assertEquals(expectedResult.getTotalElements(), actualResult.getTotalElements());
        Assertions.assertEquals(expectedResult.getBooks().get(0).getId(), actualResult.getBooks().get(0).getId());
    }

    private Set<Category> generateCategory(int id) {
        Set<Category> categories = new HashSet<>();
        List<Book> books = new ArrayList<>();
        categories.add(new Category(id, "Children`s", books));
        return categories;
    }

    private List<Author> generateAuthors(int id){
        List<Author> authors = new ArrayList<>();
        authors.add(new Author(id, "Uiljam", "Blajk",null , null));
        return authors;
    }

    @Test
    void getBookDtoById() {
    }

    @Test
    void getBooksByTitle() {
    }

    @Test
    void createBook() {
    }

    @Test
    void updateBook() {
    }

    @Test
    void deleteBook() {
    }
}