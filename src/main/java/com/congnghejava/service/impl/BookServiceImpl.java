package com.congnghejava.service.impl;

import com.congnghejava.model.Book;
import com.congnghejava.model.Category;
import com.congnghejava.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.congnghejava.service.BookService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Iterable<Book> getAll() {
        return bookRepository.getAll();
    }

    @Override
    public Iterable<Book> getBookWarning() {
        return bookRepository.getBookWarning();
    }

    @Override
    public Iterable<Book> getBookDanger() {
        return bookRepository.getBookDanger();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void delete(Book book) {
        bookRepository.delete(book);
    }

    @Override
    public void update(Long id, Book book) {
        Book b = bookRepository.getOne(id);
        b.setAll(book);
        bookRepository.save(b);
    }

    @Override
    public void updateQuantity(Long id, Integer quantity) {
        Book b = bookRepository.getOne(id);
        b.updateQuantity(quantity);
        bookRepository.save(b);
    }

    @Override
    public Iterable<Book> getHighlight() {
        return bookRepository.getHighlight();
    }

    @Override
    public Book getOne(Long id) {
        return bookRepository.getOne(id);
    }

    @Override
    public Page<Book> findPaginated(Pageable pageable) {
        int pageSize = 6;
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Book> list;
        List<Book> books = bookRepository.findAll();

        if (books.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, books.size());
            list = books.subList(startItem, toIndex);
        }

        return new PageImpl<Book>(list, PageRequest.of(currentPage, pageSize), books.size());
    }

    @Override
    public Page<Book> searchPaginated(String keyword, Pageable pageable) {
        int pageSize = 6;
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Book> list;
        List<Book> books = bookRepository.search(keyword);

        if (books.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, books.size());
            list = books.subList(startItem, toIndex);
        }

        return new PageImpl<Book>(list, PageRequest.of(currentPage, pageSize), books.size());
    }

    @Override
    public Iterable<Book> getByCategoryId(Category category) {
        return bookRepository.getByCategoryId(category);
    }

    @Override
    public Book findBySlug(String slug) {
        return bookRepository.findBySlug(slug);
    }
}
