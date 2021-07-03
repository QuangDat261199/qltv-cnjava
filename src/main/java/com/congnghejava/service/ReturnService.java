package com.congnghejava.service;


import com.congnghejava.model.Return;

import java.util.ArrayList;
import java.util.Optional;

public interface ReturnService {
    Iterable<Return> findAll();

    Optional<Return> findById(Long id);

    Iterable<Return> getReturnByMonth(String my);

    ArrayList<ArrayList> getReturnByMonthGroupByDay(String my);

    Return save(Return re);

    void delete(Return re);

    void update(Long id, Return re);

    Return getOne(Long id);
}
