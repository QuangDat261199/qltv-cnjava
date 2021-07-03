package com.congnghejava.repository;

import com.congnghejava.model.Book;
import com.congnghejava.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>{
    @Query("select b from Book b where b.highlight = true")
    Iterable<Book> getHighlight();

    @Query("select b from Book b where b.isDelete = false")
    Iterable<Book> getAll();

    @Query("select b from Book b where b.category = :category")
    Iterable<Book> getByCategoryId(Category category);

    @Query("select b from Book b where b.slug = :slug")
    Book findBySlug(String slug);

    @Query("select b from Book b where b.quantity < 5 and b.quantity > 0")
    Iterable<Book> getBookWarning();

    @Query("select b from Book b where b.quantity = 0")
    Iterable<Book> getBookDanger();

    @Query("select b from Book b where b.name like CONCAT('%',:keyword,'%')")
    List<Book> search(@Param("keyword") String keyword);
}
