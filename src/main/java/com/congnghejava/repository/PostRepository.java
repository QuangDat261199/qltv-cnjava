package com.congnghejava.repository;

import com.congnghejava.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PostRepository extends JpaRepository<Post, Long>{

    @Query("select p from Post p where p.highlight = true ")
    Iterable<Post> getHighlight();

    @Query("select p from Post p where p.slug = :slug")
    Post findBySlug(String slug);
}
