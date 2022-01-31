package com.learnjava.bookshelf.mapper;

import com.learnjava.bookshelf.dto.AuthorDto;
import com.learnjava.bookshelf.entity.Author;
import com.learnjava.bookshelf.entity.Book;
import com.learnjava.bookshelf.service.BookService;
import com.learnjava.bookshelf.service.BookServiceImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

//@Mapper//(uses = AuthorServiceImpl.class)
//public interface AuthorMapper {
////    AuthorMapper AUTHOR_MAPPER = Mappers.getMapper(AuthorMapper.class);
////    @Mapping(source = "books", target = "bookId")
////    Author toAuthor (AuthorDto authorDto);
////    Author getAuthorById(int id);
//}


@Mapper(componentModel = "spring", uses = BookService.class)
public interface AuthorMapper {
    AuthorDto authorToAuthorDto(Author author);

    Author authorDtoToAuthor(AuthorDto authorDto);

    default int fromEntity(Book book) {
        return book == null ? null : book.getId();
    }

    default Book toEntity(int id) {
        BookService bookService = new BookServiceImpl();
        return bookService.getBookById(id);
    }

    default List<Integer> fromListEntity(List<Book> books) {
        List<Integer> bookId = new ArrayList<>();
        for (Book book : books) {
            bookId.add(book.getId());
        }
        return bookId;
    }

    default List<Book> toListEntity(List<Integer> ids) {
        BookService bookService = new BookServiceImpl();
        List<Book> books = new ArrayList<>();
        for (Integer id : ids) {
            books.add(bookService.getBookById(id));
        }
        return books;
    }

}