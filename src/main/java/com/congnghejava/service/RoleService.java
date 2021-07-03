package com.congnghejava.service;


import com.congnghejava.model.Role;

import java.util.Optional;

public interface RoleService {
    Iterable<Role> findAll();

    Optional<Role> findById(Long id);

    void save(Role rule);

    void delete(Role rule);

    void update(Long id, Role rule);

    Role getOne(Long id);
}
