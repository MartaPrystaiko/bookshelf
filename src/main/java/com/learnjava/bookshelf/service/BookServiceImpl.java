package com.learnjava.bookshelf.service;

import com.learnjava.bookshelf.dao.BookDao;
import com.learnjava.bookshelf.dto.BookDto;
import com.learnjava.bookshelf.dto.BookPage;
import com.learnjava.bookshelf.entity.Author;
import com.learnjava.bookshelf.entity.Book;
import com.learnjava.bookshelf.entity.Category;
import com.learnjava.bookshelf.exception.ItemNotFoundException;
import com.learnjava.bookshelf.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AuthorService authorService;

    @Override
    public BookPage getAllBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        final Page<Book> books = bookDao.myFindAll(pageable);
        final BookPage bookPage = new BookPage();
        List<BookDto> bookDtos = new ArrayList<>();
        for (Book book : books.getContent()) {
            bookDtos.add(parseBook(book));
        }
        bookPage.setBooks(bookDtos);
        bookPage.setCurrentPage(books.getNumber());
        bookPage.setTotalElements(books.getTotalElements());
        bookPage.setLast(books.isLast());
        return bookPage;
    }

    @Override
    public BookDto getBookDtoById(int id) {
        final Optional<Book> book = bookDao.findById(id);
        return parseBook(book.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No book with such id: " + id)));
    }

    @Override
    public Book getBookById(int id) {
        final Optional<Book> book = bookDao.findById(id);
        return book.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No book with such id: " + id));
    }

    @Override
    public List<BookDto> getBooksByTitle(String title) {
        final Optional<List<Book>> books = bookDao.findByTitle(title);
        List<BookDto> bookDtos = new ArrayList<>();
        for (Book book : books.orElseThrow(() -> new ItemNotFoundException("Book", "title", title))) {
            bookDtos.add(parseBook(book));
        }
        return bookDtos;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        Book book = parseBookDto(bookDto);//parseBookDto(bookDto);
        bookDao.saveAndFlush(book);
        bookDto.setId(book.getId());
        return bookDto;
    }

    @Override
    public BookDto updateBook(int id, BookDto bookDto) {
        if (!bookDao.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No book found");
        }
        bookDto.setId(id);
        bookDao.saveAndFlush(parseBookDto(bookDto));
        return bookDto;
    }

    @Override
    public void deleteBook(int id) {
        if (!bookDao.existsById(id)) {
            throw new ItemNotFoundException("Book", "id", id);
        }
        Book book = new Book();
        book.setId(id);
        bookDao.deleteById(id);
    }


    private BookDto parseBook(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        Set<Integer> categoriesId = new HashSet<>();
        for (Category category : book.getCategories()) {
            categoriesId.add(category.getId());
        }
        bookDto.setCategoriesId(categoriesId);
        List<Integer> authorsId = new ArrayList<>();
        for (Author author : book.getAuthors()) {
            authorsId.add(author.getId());
        }
        bookDto.setAuthorsId(authorsId);
        bookDto.setPrice(book.getPrice());
        return bookDto;
    }

    private Book parseBookDto(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setTitle(bookDto.getTitle());
        Set<Category> categories = new HashSet<>();
        for (Integer categoryId : bookDto.getCategoriesId()) {
            categories.add(CategoryMapper.CATEGORY_MAPPER.categoryDtoToCategory(categoryService.getCategoryById(categoryId)));
        }
        book.setCategories(categories);
        book.setPrice(bookDto.getPrice());
        return book;
    }

}
