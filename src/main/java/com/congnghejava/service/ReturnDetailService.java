package com.congnghejava.service;


import com.congnghejava.model.ReturnDetail;

import java.util.Optional;

public interface ReturnDetailService {
    Iterable<ReturnDetail> findAll();

    Optional<ReturnDetail> findById(Long id);

    ReturnDetail save(ReturnDetail re);

    void delete(ReturnDetail re);

    void update(Long id, ReturnDetail re);

    ReturnDetail getOne(Long id);
}
