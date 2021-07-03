package com.congnghejava.service;


import com.congnghejava.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PostService {
    Iterable<Post> findAll();

    Optional<Post> findById(Long id);

    void save(Post post);

    void delete(Post post);

    void update(Long id, Post post);

    Iterable<Post> getHighlight();

    Page<Post> findPaginated(Pageable pageable);

    Post findBySlug(String slug);
}
