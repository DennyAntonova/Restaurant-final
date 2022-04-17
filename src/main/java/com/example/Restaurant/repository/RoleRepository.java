package com.example.Restaurant.repository;

import com.example.Restaurant.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT u FROM Role u WHERE u.name = ?1")
    public Role findByName(String name);
}
