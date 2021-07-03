package com.congnghejava.service.impl;

import com.congnghejava.model.ReturnDetail;
import com.congnghejava.repository.ReturnDetailRepository;
import com.congnghejava.service.ReturnDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReturnDetailServiceImpl implements ReturnDetailService {
    @Autowired
    private ReturnDetailRepository returnDetailRepository;

    @Override
    public Iterable<ReturnDetail> findAll() {
        return  returnDetailRepository.findAll();
    }

    @Override
    public Optional<ReturnDetail> findById(Long id) {
        return returnDetailRepository.findById(id);
    }

    @Override
    public ReturnDetail save(ReturnDetail returnDetail) {
        return returnDetailRepository.save(returnDetail);
    }

    @Override
    public void delete(ReturnDetail returnDetail) {
        returnDetailRepository.delete(returnDetail);
    }

    @Override
    public void update(Long id, ReturnDetail returnDetail) {

    }

    @Override
    public ReturnDetail getOne(Long id) {
        return returnDetailRepository.getOne(id);
    }

}
