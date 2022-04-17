package com.example.Restaurant.repository;


import com.example.Restaurant.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query ( "SELECT u FROM Order u WHERE u.id = ?1" )
    public Order findById(Long id);

    @Query ( "SELECT u FROM Order u WHERE u.id = ?1" )
    public List<Order> findByOrderId(Long id);

    @Query ( "SELECT p FROM Order p WHERE p.dateAndTime LIKE %?1%")
    public List <Order> search (String keyword );


}
