package com.congnghejava.service.impl;

import com.congnghejava.model.Book;
import com.congnghejava.model.Borrow;
import com.congnghejava.model.User;
import com.congnghejava.repository.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.congnghejava.service.BorrowService;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class BorrowServiceImpl implements BorrowService {
    @Autowired
    private BorrowRepository borrowRepository;

    @Override
    public Iterable<Borrow> findAll() {
        return  borrowRepository.findAll();
    }

    @Override
    public Optional<Borrow> findById(Long id) {
        return borrowRepository.findById(id);
    }

    @Override
    public ArrayList<ArrayList> getBorrowByMonthGroupByDay(String my) {
        return borrowRepository.getBorrowByMonthGroupByDay(my);
    }

    @Override
    public Borrow save(Borrow borrow) {
        return borrowRepository.save(borrow);
    }

    @Override
    public void delete(Borrow borrow) {
        borrowRepository.delete(borrow);
    }

    @Override
    public void update(Long id, Borrow borrow) {
        Borrow b = borrowRepository.getOne(id);
        b.setAll(borrow);
        borrowRepository.save(b);
    }

    @Override
    public Borrow getOne(Long id) {
        return borrowRepository.getOne(id);
    }

    @Override
    public Borrow getByUser(User user) {
        return borrowRepository.getByUser(user);
    }


}
