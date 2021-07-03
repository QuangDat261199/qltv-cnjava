package com.congnghejava.service;


import com.congnghejava.model.BorrowDetail;

import java.util.Optional;

public interface BorrowDetailService {
    Iterable<BorrowDetail> findAll();

    Optional<BorrowDetail> findById(Long id);

    BorrowDetail save(BorrowDetail borrow);

    void delete(BorrowDetail borrow);

    void update(Long id, BorrowDetail borrow);

}
