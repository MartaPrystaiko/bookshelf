package com.learnjava.bookshelf.dao;

import com.learnjava.bookshelf.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorDao extends JpaRepository<Author, Integer> {

    @Query("SELECT DISTINCT a FROM Author a LEFT JOIN FETCH a.books bks LEFT JOIN FETCH bks.categories ORDER BY a.name")
    List<Author> myFindAll();
}
