package com.congnghejava.service;

import com.congnghejava.model.Book;
import com.congnghejava.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookService {
    Iterable<Book> findAll();

    Iterable<Book> getAll();

    Iterable<Book> getBookWarning();

    Iterable<Book> getBookDanger();

    Optional<Book> findById(Long id);

    void save(Book book);

    void delete(Book book);

    void update(Long id, Book book);

    void updateQuantity(Long id, Integer quantity);

    Iterable<Book> getHighlight();

    Book getOne(Long id);

    Page<Book> findPaginated(Pageable pageable);

    Page<Book> searchPaginated(String keyword, Pageable pageable);

    Iterable<Book> getByCategoryId(Category category);

    Book findBySlug(String slug);
}
