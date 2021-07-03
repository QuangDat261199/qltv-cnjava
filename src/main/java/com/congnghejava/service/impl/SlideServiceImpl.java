package com.congnghejava.service.impl;

import com.congnghejava.model.Post;
import com.congnghejava.model.Slide;
import com.congnghejava.repository.PostRepository;
import com.congnghejava.repository.SlideRepository;
import com.congnghejava.service.PostService;
import com.congnghejava.service.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SlideServiceImpl implements SlideService {
    @Autowired
    SlideRepository slideRepository;

    @Override
    public Iterable<Slide> findAll() {
        return slideRepository.findAll();
    }

    @Override
    public Optional<Slide> findById(Long id) {
        return slideRepository.findById(id);
    }

    @Override
    public void save(Slide slide) {
        slideRepository.save(slide);
    }

    @Override
    public void delete(Slide slide) {
        slideRepository.delete(slide);
    }

    @Override
    public void update(Long id, Slide slide) {
        Slide s = slideRepository.getOne(id);
        s.setAll(slide);
        slideRepository.save(s);
    }

    @Override
    public Iterable<Slide> getActive() {
        return slideRepository.getActive();
    }
}
