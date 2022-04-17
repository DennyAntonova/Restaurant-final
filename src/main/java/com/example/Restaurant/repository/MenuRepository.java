package com.example.Restaurant.repository;


import com.example.Restaurant.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    @Query ( "SELECT u FROM Menu u WHERE u.id = ?1" )
    public List<Menu> findByMenuId(Long menuId);
}
