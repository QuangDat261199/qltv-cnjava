package com.congnghejava.service.impl;

import com.congnghejava.model.User;
import com.congnghejava.repository.UserRepository;
import com.congnghejava.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User getOne(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public Iterable<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public void update(Long id, User user) {
        User u = userRepository.getOne(id);
        u.setAll(user);
        userRepository.save(u);
    }

    @Override
    public User getByUserName(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    @Override
    public void resetToken(Long id, String token) {
        User u = userRepository.getOne(id);
        u.setResetToken(token);
        System.out.println(u.getResetToken());
        userRepository.save(u);
    }


}
