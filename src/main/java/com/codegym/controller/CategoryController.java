package com.codegym.controller;

import com.codegym.model.Category;
import com.codegym.service.BlogService;
import com.codegym.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    BlogService blogService;

    @GetMapping("/list")
    public ResponseEntity<List<Category>> listAllCategory() {
        List<Category> categories = categoryService.findAll();
        if (categories.isEmpty()) {
            return new ResponseEntity<List<Category>>(HttpStatus.NO_CONTENT); //You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createCategory(@RequestBody Category category, UriComponentsBuilder uriComponentsBuilder) {
        System.out.println("Creating Category " + category.getName());
        categoryService.save(category);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponentsBuilder.path("/view/{id}").buildAndExpand(category.getId()).toUri());
        return new ResponseEntity<Void>(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/view/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") long id, @RequestBody Category category) {
        System.out.println("Updating Category " + id);

        Category currentCategory = categoryService.findById(id);

        if (currentCategory == null) {
            System.out.println("Category with id " + id + " not found!");
            return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
        }
        currentCategory.setName(category.getName());
        currentCategory.setId(category.getId());

        categoryService.save(currentCategory);

        return new ResponseEntity<Category>(currentCategory, HttpStatus.OK);
    }

    @DeleteMapping("/view/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable("id") Long id) {
        System.out.println("Fetching & Deleting Category with id " + id);
        Category category = categoryService.findById(id);
        if (category == null) {
            System.out.println("Unable to delete. Category with id " + id + " not found!");
            return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
        }
        categoryService.remove(id);
        return new ResponseEntity<Category>(HttpStatus.NO_CONTENT);
    }
}
