package com.congnghejava.service;

import com.congnghejava.model.Category;

import java.util.Optional;

public interface CategoryService {
    Iterable<Category> findAll();

    Optional<Category> findById(Long id);

    void save(Category category);

    void delete(Category category);

    void update(Long id, Category category);

    Category findBySlug(String slug);
}
