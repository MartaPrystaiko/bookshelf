package com.learnjava.bookshelf.validator;

import com.learnjava.bookshelf.dto.BookDto;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return BookDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        BookDto bookDto = (BookDto) o;
        if (StringUtils.isBlank(bookDto.getTitle())){
            errors.rejectValue("title", "book.title.not-blank", "Book title should not be empty!");
        } else if (!CharUtils.isAsciiAlphaUpper(bookDto.getTitle().charAt(0))){
            errors.rejectValue("title", "book.title.capital-letter",
                    "Book title should start with capital letter!");
        }
    }
}
