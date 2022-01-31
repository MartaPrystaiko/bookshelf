package com.learnjava.bookshelf.dao;

import com.learnjava.bookshelf.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookDao extends JpaRepository<Book, Integer> {
    //todo: query
    @Query("SELECT b FROM Book b WHERE b.title LIKE %:title%")
    Optional<List<Book>> findByTitle(String title);

    @Query(value = "SELECT b FROM Book b LEFT JOIN FETCH b.authors LEFT JOIN FETCH b.categories ORDER BY b.id",
            countQuery = "SELECT COUNT(b) FROM Book b")
    Page<Book> myFindAll(Pageable pageable);



//            1+1+1+1+1+1+1+1+1+1+
//            1+1+1+1+1+1+1+1+1+1+
//            1+1+1+1+1+1+1+1+1+1+
//            1+1+1+1+1+1+1+1+1+1+
//            1+1+1+1+1+1+1+1+1+1+
//            1+1+1+1+1+1+1+1+1+1+
//            1+1+1+1+1+1+1+1+1+1+
//            1+1+1+1+1+1+1+1+1+1+
//            1+1+1+1+1+1+1+1+1+1+
//            1+1+1+1+1+1+1+1+1+1+500
}
