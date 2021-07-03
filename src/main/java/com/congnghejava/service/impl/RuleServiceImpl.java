package com.congnghejava.service.impl;

import com.congnghejava.model.Rule;
import com.congnghejava.model.User;
import com.congnghejava.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.congnghejava.service.RuleService;

import java.util.Optional;

@Service
public class RuleServiceImpl implements RuleService {
    @Autowired RuleRepository ruleRepository;

    @Override
    public Iterable<Rule> findAll() {
        return ruleRepository.findAll();
    }

    @Override
    public Optional<Rule> findById(Long id) {
        return ruleRepository.findById(id);
    }

    @Override
    public void save(Rule rule) {
        ruleRepository.save(rule);
    }

    @Override
    public void delete(Rule rule) {
        ruleRepository.delete(rule);
    }

    @Override
    public void update(Long id, Rule rule) {
        Rule r = ruleRepository.getOne(id);
        r.setAll(rule);
        ruleRepository.save(r);
    }
}
