package com.congnghejava.repository;

import com.congnghejava.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CategoryRepository extends JpaRepository<Category, Long>{

    @Query("select c from Category c where c.slug = :slug")
    Category findBySlug(String slug);
}
