package com.congnghejava.service.impl;

import com.congnghejava.model.Role;
import com.congnghejava.model.Rule;
import com.congnghejava.repository.RoleRepository;
import com.congnghejava.repository.RuleRepository;
import com.congnghejava.service.RoleService;
import com.congnghejava.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public Iterable<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void delete(Role role) {
        roleRepository.delete(role);
    }

    @Override
    public void update(Long id, Role role) {
        Role r = roleRepository.getOne(id);
//        r.setAll(role);
        roleRepository.save(r);
    }

    @Override
    public Role getOne(Long id) {
        return roleRepository.getOne(id);
    }
}
