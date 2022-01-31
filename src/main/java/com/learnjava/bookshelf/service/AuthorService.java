package com.learnjava.bookshelf.service;

import com.learnjava.bookshelf.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> getAllAuthors();

    AuthorDto getAuthorDtoById(int id);

    AuthorDto createAuthor(AuthorDto authorDto);

    AuthorDto updateAuthor(int id, AuthorDto authorDto);

    void deleteAuthor(int id);

}
