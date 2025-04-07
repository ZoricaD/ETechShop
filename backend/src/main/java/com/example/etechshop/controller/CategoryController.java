package com.example.etechshop.controller;

import com.example.etechshop.entity.Category;
import com.example.etechshop.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return categories.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id) != null
                ? ResponseEntity.ok(categoryService.getCategoryById(id))
                : ResponseEntity.status(404).body("Category not found");
    }

    @PostMapping("/add")
    public ResponseEntity<?> createCategory(@RequestBody(required = false) Category category) {
        if (category == null || category.getName() == null || category.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Category name cannot be empty");
        }
        try {
            return ResponseEntity.ok(categoryService.createCategory(category.getName()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error creating category: " + e.getMessage());
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody(required = false) Category category) {
        if (category == null || category.getName() == null || category.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Category name cannot be empty");
        }
        try {
            return ResponseEntity.ok(categoryService.updateCategory(id, category.getName()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Category not found: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok().body("Category deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Category not found: " + e.getMessage());
        }
    }
}