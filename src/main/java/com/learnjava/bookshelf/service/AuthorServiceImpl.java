package com.learnjava.bookshelf.service;

import com.learnjava.bookshelf.dao.AuthorDao;
import com.learnjava.bookshelf.dto.AuthorDto;
import com.learnjava.bookshelf.entity.Author;
import com.learnjava.bookshelf.entity.Book;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    AuthorDao authorDao;

    @Override
    public List<AuthorDto> getAllAuthors() {
        List<Author> authors = authorDao.myFindAll();
        List<AuthorDto> authorDtos = new ArrayList<>();
        for (Author author : authors) {
            authorDtos.add(parseAuthor(author));
        }
        return authorDtos;
    }

    @Override
    public AuthorDto getAuthorDtoById(int id) {
        final Optional<Author> author = authorDao.findById(id);
        return parseAuthor(author.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No author with such id: " + id)));
    }

    private Author getAuthorById(int id) {
        final Optional<Author> author = authorDao.findById(id);
        return author.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No author with such id: " + id));
    }

    @Override
    public AuthorDto createAuthor(AuthorDto authorDto) {
        authorDao.saveAndFlush(parseAuthorDto(authorDto));
        return authorDto;
    }

    @Override
    public AuthorDto updateAuthor(int id, AuthorDto authorDto) {
        isIdOrThrowException(id);
        authorDto.setId(id);
        authorDao.saveAndFlush(parseAuthorDto(authorDto));
        return authorDto;
    }

    @Override
    public void deleteAuthor(int id) {
        isIdOrThrowException(id);

        authorDao.deleteById(id);
        authorDao.flush();
    }

    private AuthorDto parseAuthor(Author author) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(author.getId());
        authorDto.setName(author.getName());
        authorDto.setSurname(author.getSurname());
        final List<Integer> booksId = author.getBooks().stream()
                .map(Book::getId)
                .collect(Collectors.toList());
        authorDto.setBookId(booksId);
        return authorDto;
    }

    private Author parseAuthorDto(AuthorDto authorDto) {
        validateAuthorDto(authorDto);

        Author author = new Author();
        author.setId(authorDto.getId());
        author.setName(authorDto.getName());
        author.setSurname(authorDto.getSurname());
        return author;
    }

    public void validateAuthorDto(AuthorDto authorDto) {
        if (StringUtils.isBlank(authorDto.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author`s name should not be empty!");
        }
        if (!CharUtils.isAsciiAlphaUpper(authorDto.getName().charAt(0))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author`s name should starts with capital letter!");
        }
        if (StringUtils.isNotBlank(authorDto.getSurname()) && !CharUtils.isAsciiAlphaUpper(authorDto.getSurname().charAt(0))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author`s surname should starts with capital letter!");
        }
    }

    private void isIdOrThrowException(int id) {
        if (!authorDao.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No author with such id: " + id);
        }
    }
}
