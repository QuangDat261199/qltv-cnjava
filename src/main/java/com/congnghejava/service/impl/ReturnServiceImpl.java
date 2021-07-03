package com.congnghejava.service.impl;

import com.congnghejava.model.Return;
import com.congnghejava.repository.ReturnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.congnghejava.service.ReturnService;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ReturnServiceImpl implements ReturnService {
    @Autowired
    ReturnRepository returnRepository;

    @Override
    public Iterable<Return> findAll() {
        return returnRepository.findAll();
    }

    @Override
    public Optional<Return> findById(Long id) {
        return returnRepository.findById(id);
    }

    @Override
    public Iterable<Return> getReturnByMonth(String my) {
        return returnRepository.getReturnByMonth(my);
    }

    @Override
    public ArrayList<ArrayList> getReturnByMonthGroupByDay(String my) {
        return returnRepository.getReturnByMonthGroupByDay(my);
    }

    @Override
    public Return save(Return re) {
        return returnRepository.save(re);
    }

    @Override
    public void delete(Return re) {
        returnRepository.delete(re);
    }

    @Override
    public void update(Long id, Return re) {

    }

    @Override
    public Return getOne(Long id) {
        return returnRepository.getOne(id);
    }
}
