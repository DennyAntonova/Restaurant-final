package com.example.Restaurant.controller;

import com.example.Restaurant.*;
import com.example.Restaurant.entity.Order;
import com.example.Restaurant.entity.Receipt;
import com.example.Restaurant.repository.OrderRepository;
import com.example.Restaurant.repository.ReceiptRepository;
import com.example.Restaurant.repository.UserRepository;
import com.example.Restaurant.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;


@Controller
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ReceiptRepository receiptRepository;

   @Autowired
    private OrderService service;

    @GetMapping ( "/order-create" )
    public String showOrderForm(Order order, Model model) {
        model.addAttribute("order", order);
        return "order-create";
    }

    @PostMapping ( "/order-create" )
    public String addOrder(@Valid Order order, BindingResult bindingResult) {

        try {
            if (bindingResult.hasErrors()) {
                return "order-create";
            }
            List<Order> orders = orderRepository.findAll();
            for (int i = 0; i < orders.size(); i++) {
                if (Objects.equals(orders.get(i).getRestaurantTable(), order.getRestaurantTable())
                        && !(orders.get(i).getStatus().equals(StatusOrder.PAID))) {
                    return "error-valid";
                }
            }
            orderRepository.save(order);

            return "redirect:/order-create";
        } catch (Exception e) {
            return "error-valid";
        }
    }


    @GetMapping ( "/order-edit/{id}" )
    public String orderEdit(@PathVariable long id, Model model) {
        Order status = orderRepository.findById(id);
        model.addAttribute("order", status);
        return "order-edit";
    }


    @PostMapping ( "/order-update/{id}" )
    public String orderUpdate(@PathVariable long id, Order order, Model model) {
        List<Order> orders = orderRepository.findByOrderId(id);
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId() == id) {
                order.setRestaurantTable(orders.get(i).getRestaurantTable());
                order.setDateAndTime(orders.get(i).getDateAndTime());
                order.setUserId(orders.get(i).getUserId());
            }
        }
        order.setId(id);
        orderRepository.save(order);
        List<Order> listOrders = orderRepository.findByOrderId(id);
        for (int i = 0; i < listOrders.size(); i++) {
            if (listOrders.get(i).getStatus().equals(StatusOrder.PAID)) {
                List<Receipt> receipt = receiptRepository.findByOrderId(id);
                model.addAttribute("receipt", receipt);
                return "/summary-receipt";
            }

        }
        return "redirect:/order-list";
    }


    @GetMapping ( "/order-list" )
    public String allOrders(Model model) {
        List<Order> allOrders = orderRepository.findAll();
        model.addAttribute("order", allOrders);
        return "order-list";
    }

    @GetMapping ( "/active-orders" )
    public String allOrdersFilter(Model model) {
        List<Order> allOrders = orderRepository.findAll();
        for (int i = 0; i < allOrders.size(); i++) {
            if (Objects.equals(allOrders.get(i).getStatus(), StatusOrder.PAID)) {
                allOrders.remove(allOrders.get(i));
                model.addAttribute("order", allOrders);
            }
        }
        model.addAttribute("order", allOrders);
        return "active-orders";
    }

    @GetMapping ( "/new-orders" )
    public String allNewOrders(Model model) {
        List<Order> allOrders = orderRepository.findAll();
        for (int i = 0; i < allOrders.size(); i++) {

            if (!allOrders.get(i).getStatus().equals(StatusOrder.CREATED)) {
                allOrders.add(allOrders.get(i));
                model.addAttribute("order", allOrders);
            }
        }
        model.addAttribute("order", allOrders);
        return "new-orders";
    }
    @GetMapping("/sort-orders")
    public String sortOrders(Model model) {
        List<Order> listOrders = orderRepository.findAll();
        model.addAttribute("listOrders", listOrders);

        return "sort-orders";
    }

    @GetMapping ( "/sort-table-by-date-ASC" )
    public String sortTableByDateASC(Model model) {
        List<Order> listOrders = orderRepository.findAll(Sort.by(Sort.Direction.ASC, "dateAndTime"));
        model.addAttribute("listOrders", listOrders);
        return "sort-orders";
    }

    @GetMapping ( "/sort-table-by-date-DESC" )
    public String sortTableByDateDESC(Model model) {
        List<Order> listOrders = orderRepository.findAll(Sort.by(Sort.Direction.DESC, "dateAndTime"));
        model.addAttribute("listOrders", listOrders);
        return "sort-orders";
    }

    @RequestMapping( "/order-list" )
    public String searchByDate(Model model, @Param ( "keyword" ) String keyword) {
        List<Order> listRestaurantTable = service.listAll(keyword);
        model.addAttribute("listRestaurantTable", listRestaurantTable);
        model.addAttribute("keyword", keyword);

        return "order-list";
    }
}
