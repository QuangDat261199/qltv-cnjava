package com.congnghejava.service;


import com.congnghejava.model.Rule;
import com.congnghejava.model.User;

import java.util.Optional;

public interface RuleService {
    Iterable<Rule> findAll();

    Optional<Rule> findById(Long id);

    void save(Rule rule);

    void delete(Rule rule);

    void update(Long id, Rule rule);
}
