package com.congnghejava.repository;

import com.congnghejava.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ContactRepository extends JpaRepository<Contact, Long>{

}
