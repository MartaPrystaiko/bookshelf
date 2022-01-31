package com.learnjava.bookshelf.controller;

import com.learnjava.bookshelf.dto.BookDto;
import com.learnjava.bookshelf.dto.BookPage;
import com.learnjava.bookshelf.service.BookService;
import com.learnjava.bookshelf.validator.BookValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/book")
public class BookController {

    private static final Logger LOG = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;
    @Autowired
    private BookValidator bookValidator;

    //    @RequestMapping(value = "/book", method = RequestMethod.GET)
    @GetMapping
    public BookPage getBooks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        return bookService.getAllBooks(page, size);
    }

    @GetMapping(value = "/{id}")
    public BookDto getBookById(@PathVariable int id) {
        return bookService.getBookDtoById(id);
    }

    @GetMapping(value = "/title/{title}")
    public List<BookDto> getBooksByTitle(@PathVariable String title) {
        return bookService.getBooksByTitle(title);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto insertBook(@RequestBody @Valid BookDto bookDto) {
        LOG.info("Handling POST request for object {}", bookDto);
        return bookService.createBook(bookDto);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BookDto updateBook(@PathVariable int id, @RequestBody @Valid BookDto bookDto) {
        LOG.info("Handling PUT request for object {} for id = {}", bookDto, id);
        return bookService.updateBook(id, bookDto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        LOG.info("Handling DELETE request for object with id: {}", id);
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.addValidators(bookValidator);
    }
}
