package com.congnghejava.service.impl;

import com.congnghejava.model.Borrow;
import com.congnghejava.model.BorrowDetail;
import com.congnghejava.repository.BorrowDetailRepository;
import com.congnghejava.repository.BorrowRepository;
import com.congnghejava.service.BorrowDetailService;
import com.congnghejava.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BorrowDetailServiceImpl implements BorrowDetailService {
    @Autowired
    private BorrowDetailRepository borrowDetailRepository;

    @Override
    public Iterable<BorrowDetail> findAll() {
        return  borrowDetailRepository.findAll();
    }

    @Override
    public Optional<BorrowDetail> findById(Long id) {
        return borrowDetailRepository.findById(id);
    }

    @Override
    public BorrowDetail save(BorrowDetail borrowDetail) {
        return borrowDetailRepository.save(borrowDetail);
    }

    @Override
    public void delete(BorrowDetail borrowDetail) {
        borrowDetailRepository.delete(borrowDetail);
    }

    @Override
    public void update(Long id, BorrowDetail borrowDetail) {

    }

}
