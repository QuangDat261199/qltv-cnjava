package com.congnghejava.repository;

import com.congnghejava.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long>{

    @Query("select u from User u where u.username = :username")
    User getUserByUsername(@Param("username") String username);

    @Query("select u from User u where u.role = 'user'")
    Iterable<User> getAllUsers();

    @Query("select u from User u where u.email = :email")
    User getByEmail(@Param("email") String email);
}
