package com.learnjava.bookshelf.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.learnjava.bookshelf.dto.BookDto;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.springframework.validation.BindException;

class BookValidatorTest {
    @Test
    void testValidate() {
        BookValidator bookValidator = new BookValidator();
        BookDto bookDto = new BookDto();
        BindException bindException = new BindException(bookDto, "com.learnjava.bookshelf.dto.BookDto");

        bookValidator.validate(bookDto, bindException);
        assertEquals(
                "org.springframework.validation.BindException: org.springframework.validation.BeanPropertyBindingResult:"
                        + " 1 errors\n"
                        + "Field error in object 'com.learnjava.bookshelf.dto.BookDto' on field 'title': rejected value [null];"
                        + " codes [book.title.not-blank.com.learnjava.bookshelf.dto.BookDto.title,book.title.not-blank.title,book"
                        + ".title.not-blank.java.lang.String,book.title.not-blank]; arguments []; default message [Book title"
                        + " should not be empty!]",
                bindException.toString());
        assertTrue(bindException.getPropertyEditorRegistry() instanceof org.springframework.beans.BeanWrapperImpl);
        assertEquals(1, bindException.getBindingResult().getErrorCount());
    }

    @Test
    void testValidate2() {
        BookValidator bookValidator = new BookValidator();
        HashSet<Integer> categoriesId = new HashSet<>();
        BookDto bookDto = new BookDto(1, "Dr", categoriesId, 10.0, new ArrayList<>());

        bookValidator.validate(bookDto, new BindException(bookDto, "com.learnjava.bookshelf.dto.BookDto"));
        assertTrue(bookDto.getAuthorsId().isEmpty());
        assertEquals("Dr", bookDto.getTitle());
        assertEquals(10.0, bookDto.getPrice());
        assertEquals(1, bookDto.getId());
        assertTrue(bookDto.getCategoriesId().isEmpty());
    }

    @Test
    void testValidate3() {
        BookValidator bookValidator = new BookValidator();
        BookDto bookDto = new BookDto();
        BindException bindException = new BindException(bookDto, "Object Name");

        bookValidator.validate(bookDto, bindException);
        assertEquals(
                "org.springframework.validation.BindException: org.springframework.validation.BeanPropertyBindingResult:"
                        + " 1 errors\n"
                        + "Field error in object 'Object Name' on field 'title': rejected value [null]; codes [book.title.not"
                        + "-blank.Object Name.title,book.title.not-blank.title,book.title.not-blank.java.lang.String,book.title"
                        + ".not-blank]; arguments []; default message [Book title should not be empty!]",
                bindException.toString());
        assertTrue(bindException.getPropertyEditorRegistry() instanceof org.springframework.beans.BeanWrapperImpl);
        assertEquals(1, bindException.getBindingResult().getErrorCount());
    }

    @Test
    void testValidate4() {
        BookValidator bookValidator = new BookValidator();
        HashSet<Integer> categoriesId = new HashSet<>();
        BookDto bookDto = new BookDto(1, "42", categoriesId, 10.0, new ArrayList<>());

        BindException bindException = new BindException(bookDto, "com.learnjava.bookshelf.dto.BookDto");

        bookValidator.validate(bookDto, bindException);
        assertEquals(
                "org.springframework.validation.BindException: org.springframework.validation.BeanPropertyBindingResult:"
                        + " 1 errors\n"
                        + "Field error in object 'com.learnjava.bookshelf.dto.BookDto' on field 'title': rejected value [42];"
                        + " codes [book.title.capital-letter.com.learnjava.bookshelf.dto.BookDto.title,book.title.capital-letter"
                        + ".title,book.title.capital-letter.java.lang.String,book.title.capital-letter]; arguments []; default"
                        + " message [Book title should start with capital letter!]",
                bindException.toString());
        assertTrue(bindException.getPropertyEditorRegistry() instanceof org.springframework.beans.BeanWrapperImpl);
        assertEquals(1, bindException.getBindingResult().getErrorCount());
    }
}

