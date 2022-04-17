package com.example.Restaurant.repository;

import com.example.Restaurant.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    @Query ( "SELECT u FROM User u WHERE u.email = ?1" )
    public User findByEmail(String email);

    @Query ( "SELECT u FROM User u WHERE u.id = ?1" )
    public Optional<User> findById (Long id);
}
