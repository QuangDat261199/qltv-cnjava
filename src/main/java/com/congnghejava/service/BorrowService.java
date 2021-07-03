package com.congnghejava.service;


import com.congnghejava.model.Borrow;
import com.congnghejava.model.User;

import java.util.ArrayList;
import java.util.Optional;

public interface BorrowService {
    Iterable<Borrow> findAll();

    Optional<Borrow> findById(Long id);

    ArrayList<ArrayList> getBorrowByMonthGroupByDay(String my);

    Borrow save(Borrow borrow);

    void delete(Borrow borrow);

    void update(Long id, Borrow borrow);

    Borrow getOne(Long id);

    Borrow getByUser(User user);

}
