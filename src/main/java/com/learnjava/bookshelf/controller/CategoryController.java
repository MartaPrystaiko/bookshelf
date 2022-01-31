package com.learnjava.bookshelf.controller;

import com.learnjava.bookshelf.dto.CategoryDto;
import com.learnjava.bookshelf.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping(value = "/{id}")
    public CategoryDto getCategoryById(@PathVariable int id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto insertCategory(@RequestBody CategoryDto categoryDto) {
        log.info("Handling POST request for object {}", categoryDto);
        return categoryService.createCategory(categoryDto);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CategoryDto updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable int id) {
        log.info("Handling PUT request for object {} for id = {}", categoryDto, id);
        return categoryService.updateCategory(categoryDto, id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable int id) {
        categoryService.deleteCategory(id);
        log.info("Handling DELETE request for object with id: {}", id);
    }
}
