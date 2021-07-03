package com.congnghejava.service;


import com.congnghejava.model.Slide;

import java.util.Optional;

public interface SlideService {
    Iterable<Slide> findAll();

    Optional<Slide> findById(Long id);

    void save(Slide slide);

    void delete(Slide slide);

    void update(Long id, Slide slide);

    Iterable<Slide> getActive();
}
