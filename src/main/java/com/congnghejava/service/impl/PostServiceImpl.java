package com.congnghejava.service.impl;


import com.congnghejava.model.Post;
import com.congnghejava.repository.PostRepository;
import com.congnghejava.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    PostRepository postRepository;

    @Override
    public Iterable<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public void save(Post post) {
        postRepository.save(post);
    }

    @Override
    public void delete(Post post) {
        postRepository.delete(post);
    }

    @Override
    public void update(Long id, Post post) {
        Post p = postRepository.getOne(id);
        p.setAll(post);
        postRepository.save(p);
    }

    @Override
    public Iterable<Post> getHighlight() {
        return postRepository.getHighlight();
    }

    @Override
    public Page<Post> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Post> list;
        List<Post> posts = postRepository.findAll();
        if (posts.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, posts.size());
            list = posts.subList(startItem, toIndex);
        }

        return new PageImpl<Post>(list, PageRequest.of(currentPage, pageSize), posts.size());
    }

    @Override
    public Post findBySlug(String slug) {
        return postRepository.findBySlug(slug);
    }


}
