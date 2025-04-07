package com.example.etechshop.service;

import com.example.etechshop.entity.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category createCategory(String name);
    Category getCategoryById(Long id);
    Optional<Category> getCategoryByName(String name);
    List<Category> getAllCategories();
    Category updateCategory(Long id, String newName);
    void deleteCategory(Long id);
}
