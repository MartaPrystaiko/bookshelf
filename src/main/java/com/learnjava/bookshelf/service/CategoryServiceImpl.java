package com.learnjava.bookshelf.service;

import com.learnjava.bookshelf.dao.CategoryDao;
import com.learnjava.bookshelf.dto.CategoryDto;
import com.learnjava.bookshelf.entity.Category;
import com.learnjava.bookshelf.exception.ItemNotFoundException;
import com.learnjava.bookshelf.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDao categoryDao;

    @Override
    public List<CategoryDto> getAllCategories() {
        final List<Category> categories = categoryDao.findAll();
        List<CategoryDto> categoryDtos = new ArrayList<>();

        for (Category category : categories) {
            categoryDtos.add(CategoryMapper.CATEGORY_MAPPER.categoryToCategoryDto(category));//parseCategory(category));
        }
        return categoryDtos;
    }

    @Override
    public CategoryDto getCategoryById(int id) {
        final Optional<Category> category = categoryDao.findById(id);
        return CategoryMapper.CATEGORY_MAPPER.categoryToCategoryDto(category.orElseThrow(()-> new ItemNotFoundException("Category", "id", id)));
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        if (!categoryDto.getName().isEmpty()) {
            final Category category = CategoryMapper.CATEGORY_MAPPER.categoryDtoToCategory(categoryDto);//parseCategoryDto(categoryDto);
            categoryDao.saveAndFlush(category);
            categoryDto.setId(category.getId());
            return categoryDto;
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Name of category should not be empty");
        }
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, int id) {
        if (categoryDao.existsById(id)) {
            categoryDto.setId(id);
            Category category = CategoryMapper.CATEGORY_MAPPER.categoryDtoToCategory(categoryDto);//parseCategoryDto(categoryDto);
            categoryDao.saveAndFlush(category);
            return categoryDto;
        } else {
            throw new ItemNotFoundException("Category", "id", id);
        }
    }

    @Override
    public void deleteCategory(int id) {
        if (categoryDao.existsById(id)) {
            categoryDao.deleteById(id);
            categoryDao.flush();
        }  else {
            throw new ItemNotFoundException("Category", "id", id);
        }
    }

//    public CategoryDto parseCategory(Category category) {
//        CategoryDto categoryDto = new CategoryDto();
////        categoryDto.setId(category.getId());
////        categoryDto.setName(category.getName());
//        return categoryDto;
//    }
//
//    public Category parseCategoryDto(CategoryDto categoryDto) {
//        Category category = new Category();
////        category.setId(categoryDto.getId());
////        category.setName(categoryDto.getName());
//        return category;
//    }
}
