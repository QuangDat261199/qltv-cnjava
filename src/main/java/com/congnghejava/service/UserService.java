package com.congnghejava.service;


import com.congnghejava.model.User;

import java.util.Optional;

public interface UserService {
    Iterable<User> findAll();

    User getOne(Long id);

    Iterable<User> getAllUsers();

    Optional<User> findById(Long id);

    void save(User user);

    void delete(User user);

    void update(Long id, User user);

    User getByUserName(String username);

    User getByEmail(String email);

    void resetToken(Long id, String token);
}
