package com.learnjava.bookshelf.service;

import com.learnjava.bookshelf.dto.CategoryDto;
import com.learnjava.bookshelf.entity.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories();

    CategoryDto getCategoryById(int id);

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto, int id);

    void deleteCategory(int id);

//    Category parseCategoryDto(CategoryDto categoryDto);
}
