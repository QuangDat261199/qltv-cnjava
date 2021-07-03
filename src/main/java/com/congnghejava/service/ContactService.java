package com.congnghejava.service;


import com.congnghejava.model.Contact;

import java.util.Optional;

public interface ContactService {
    Iterable<Contact> findAll();

    Optional<Contact> findById(Long id);

    void save(Contact contact);

    void delete(Contact contact);
}
