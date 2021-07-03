package com.congnghejava.repository;

import com.congnghejava.model.Slide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface SlideRepository extends JpaRepository<Slide, Long>{

    @Query("select s from Slide s where s.isActive = true")
    Iterable<Slide> getActive();
}
