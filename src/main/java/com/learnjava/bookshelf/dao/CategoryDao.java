package com.learnjava.bookshelf.dao;

import com.learnjava.bookshelf.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<Category, Integer> {
}
