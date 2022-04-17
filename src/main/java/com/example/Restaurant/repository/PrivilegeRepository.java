package com.example.Restaurant.repository;


import com.example.Restaurant.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    @Query ( "SELECT u FROM Privilege u WHERE u.name = ?1" )
    public Privilege findByName(String name);
}
