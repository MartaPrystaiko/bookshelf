package com.learnjava.bookshelf.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.learnjava.bookshelf.dao.AuthorDao;
import com.learnjava.bookshelf.dto.AuthorDto;
import com.learnjava.bookshelf.entity.Author;

import java.time.LocalDate;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

@ContextConfiguration(classes = {AuthorServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AuthorServiceImplTest {
    @MockBean
    private AuthorDao authorDao;

    @Autowired
    private AuthorServiceImpl authorServiceImpl;


    @Test
    void testCreateAuthor() {
        assertThrows(ResponseStatusException.class, () -> this.authorServiceImpl.createAuthor(new AuthorDto()));
    }

    @Test
    void testCreateAuthor2() {
        Author author = new Author();
        author.setBirthDate(LocalDate.ofEpochDay(1L));
        author.setBooks(new ArrayList<>());
        author.setId(1);
        author.setName("Name");
        author.setSurname("Doe");
        when(this.authorDao.saveAndFlush((Author) any())).thenReturn(author);
        AuthorDto authorDto = new AuthorDto(1, "Author`s name should not be empty!", "Doe", new ArrayList<>());

        assertSame(authorDto, this.authorServiceImpl.createAuthor(authorDto));
        verify(this.authorDao).saveAndFlush((Author) any());
        assertTrue(this.authorServiceImpl.getAllAuthors().isEmpty());
    }

    @Test
    void testCreateAuthor3() {
        when(this.authorDao.saveAndFlush((Author) any())).thenThrow(new ResponseStatusException(HttpStatus.CONTINUE));
        assertThrows(ResponseStatusException.class, () -> this.authorServiceImpl
                .createAuthor(new AuthorDto(1, "Author`s name should not be empty!", "Doe", new ArrayList<>())));
        verify(this.authorDao).saveAndFlush((Author) any());
    }

    @Test
    void testCreateAuthor4() {
        Author author = new Author();
        author.setBirthDate(LocalDate.ofEpochDay(1L));
        author.setBooks(new ArrayList<>());
        author.setId(1);
        author.setName("Name");
        author.setSurname("Doe");
        when(this.authorDao.saveAndFlush((Author) any())).thenReturn(author);
        assertThrows(ResponseStatusException.class,
                () -> this.authorServiceImpl.createAuthor(new AuthorDto(1, "42", "Doe", new ArrayList<>())));
    }

    @Test
    void testCreateAuthor5() {
        Author author = new Author();
        author.setBirthDate(LocalDate.ofEpochDay(1L));
        author.setBooks(new ArrayList<>());
        author.setId(1);
        author.setName("Name");
        author.setSurname("Doe");
        when(this.authorDao.saveAndFlush((Author) any())).thenReturn(author);
        assertThrows(ResponseStatusException.class, () -> this.authorServiceImpl
                .createAuthor(new AuthorDto(1, "Author`s name should not be empty!", "42", new ArrayList<>())));
    }

    @Test
    void testCreateAuthor6() {
        Author author = new Author();
        author.setBirthDate(LocalDate.ofEpochDay(1L));
        author.setBooks(new ArrayList<>());
        author.setId(1);
        author.setName("Name");
        author.setSurname("Doe");
        when(this.authorDao.saveAndFlush((Author) any())).thenReturn(author);
        AuthorDto authorDto = new AuthorDto(1, "Author`s name should not be empty!", "", new ArrayList<>());

        assertSame(authorDto, this.authorServiceImpl.createAuthor(authorDto));
        verify(this.authorDao).saveAndFlush((Author) any());
        assertTrue(this.authorServiceImpl.getAllAuthors().isEmpty());
    }

    @Test
    void testCreateAuthor7() {
        Author author = new Author();
        author.setBirthDate(LocalDate.ofEpochDay(1L));
        author.setBooks(new ArrayList<>());
        author.setId(1);
        author.setName("Name");
        author.setSurname("Doe");
        when(this.authorDao.saveAndFlush((Author) any())).thenReturn(author);
        AuthorDto authorDto = mock(AuthorDto.class);
        when(authorDto.getSurname()).thenThrow(new ResponseStatusException(HttpStatus.CONTINUE));
        when(authorDto.getName()).thenReturn("Name");
        assertThrows(ResponseStatusException.class, () -> this.authorServiceImpl.createAuthor(authorDto));
        verify(authorDto, atLeast(1)).getName();
        verify(authorDto).getSurname();
    }

    @Test
    void testCreateAuthor8() {
        Author author = new Author();
        author.setBirthDate(LocalDate.ofEpochDay(1L));
        author.setBooks(new ArrayList<>());
        author.setId(1);
        author.setName("Name");
        author.setSurname("Doe");
        when(this.authorDao.saveAndFlush((Author) any())).thenReturn(author);
        AuthorDto authorDto = mock(AuthorDto.class);
        when(authorDto.getSurname()).thenThrow(new ResponseStatusException(HttpStatus.CONTINUE));
        when(authorDto.getName()).thenReturn("foo");
        assertThrows(ResponseStatusException.class, () -> this.authorServiceImpl.createAuthor(authorDto));
        verify(authorDto, atLeast(1)).getName();
    }

    @Test
    void testValidateAuthorDto() {
        assertThrows(ResponseStatusException.class, () -> this.authorServiceImpl.validateAuthorDto(new AuthorDto()));
    }

    @Test
    void testValidateAuthorDto2() {
        AuthorDto authorDto = new AuthorDto(1, "Author`s name should not be empty!", "Doe", new ArrayList<>());

        this.authorServiceImpl.validateAuthorDto(authorDto);
        assertTrue(authorDto.getBookId().isEmpty());
        assertEquals("Doe", authorDto.getSurname());
        assertEquals("Author`s name should not be empty!", authorDto.getName());
        assertEquals(1, authorDto.getId());
        assertTrue(this.authorServiceImpl.getAllAuthors().isEmpty());
    }

    @Test
    void testValidateAuthorDto3() {
        AuthorDto authorDto = mock(AuthorDto.class);
        when(authorDto.getSurname()).thenReturn("Doe");
        when(authorDto.getName()).thenReturn("Name");
        this.authorServiceImpl.validateAuthorDto(authorDto);
        verify(authorDto, atLeast(1)).getName();
        verify(authorDto, atLeast(1)).getSurname();
        assertTrue(this.authorServiceImpl.getAllAuthors().isEmpty());
    }

    @Test
    void testValidateAuthorDto4() {
        AuthorDto authorDto = mock(AuthorDto.class);
        when(authorDto.getSurname()).thenThrow(new ResponseStatusException(HttpStatus.CONTINUE));
        when(authorDto.getName()).thenReturn("Name");
        assertThrows(ResponseStatusException.class, () -> this.authorServiceImpl.validateAuthorDto(authorDto));
        verify(authorDto, atLeast(1)).getName();
        verify(authorDto).getSurname();
    }

    @Test
    void testValidateAuthorDto5() {
        AuthorDto authorDto = mock(AuthorDto.class);
        when(authorDto.getSurname()).thenReturn("foo");
        when(authorDto.getName()).thenReturn("Name");
        assertThrows(ResponseStatusException.class, () -> this.authorServiceImpl.validateAuthorDto(authorDto));
        verify(authorDto, atLeast(1)).getName();
        verify(authorDto, atLeast(1)).getSurname();
    }

    @Test
    void testValidateAuthorDto6() {
        AuthorDto authorDto = mock(AuthorDto.class);
        when(authorDto.getSurname()).thenReturn("42");
        when(authorDto.getName()).thenReturn("Name");
        assertThrows(ResponseStatusException.class, () -> this.authorServiceImpl.validateAuthorDto(authorDto));
        verify(authorDto, atLeast(1)).getName();
        verify(authorDto, atLeast(1)).getSurname();
    }

    @Test
    void testValidateAuthorDto7() {
        AuthorDto authorDto = mock(AuthorDto.class);
        when(authorDto.getSurname()).thenReturn("");
        when(authorDto.getName()).thenReturn("Name");
        this.authorServiceImpl.validateAuthorDto(authorDto);
        verify(authorDto, atLeast(1)).getName();
        verify(authorDto).getSurname();
        assertTrue(this.authorServiceImpl.getAllAuthors().isEmpty());
    }

    @Test
    void testValidateAuthorDto8() {
        AuthorDto authorDto = mock(AuthorDto.class);
        when(authorDto.getSurname()).thenReturn("Doe");
        when(authorDto.getName()).thenReturn("foo");
        assertThrows(ResponseStatusException.class, () -> this.authorServiceImpl.validateAuthorDto(authorDto));
        verify(authorDto, atLeast(1)).getName();
    }
}

