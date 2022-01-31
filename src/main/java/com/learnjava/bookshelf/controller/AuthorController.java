package com.learnjava.bookshelf.controller;

import com.learnjava.bookshelf.dto.AuthorDto;
import com.learnjava.bookshelf.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "/author")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @GetMapping
    public List<AuthorDto> getAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping(value = "/{id}")
    public AuthorDto getAuthorById(@PathVariable int id) {
        return authorService.getAuthorDtoById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDto createAuthor(@RequestBody AuthorDto authorDto) {
        log.info("Handling POST request for object {}", authorDto);
        return authorService.createAuthor(authorDto);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public AuthorDto updateAuthor(@PathVariable int id, @RequestBody AuthorDto authorDto) {
        log.info("Handling PUT request for object {} for id = {}", authorDto, id);
        return authorService.updateAuthor(id, authorDto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable int id) {
        authorService.deleteAuthor(id);
        log.info("Handling DELETE request for object with id: {}", id);
    }
}
