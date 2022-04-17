package com.example.Restaurant.service;

import com.example.Restaurant.entity.Order;
import com.example.Restaurant.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> listAll(String keyword) {
        if (keyword != null) {
            return orderRepository.search(keyword);
        }
        return orderRepository.findAll();
    }
}
