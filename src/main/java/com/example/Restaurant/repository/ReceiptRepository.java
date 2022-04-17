package com.example.Restaurant.repository;

import com.example.Restaurant.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReceiptRepository extends JpaRepository<Receipt,Long> {
    @Query ( "SELECT u FROM Receipt u WHERE u.order.id = ?1" )
    public List<Receipt> findByOrderId(Long orderId);

}