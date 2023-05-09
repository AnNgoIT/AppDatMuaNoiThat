package com.example.demo.DAO;

import com.example.demo.Entity.Order;
import com.example.demo.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderDAO {
    @Autowired
    OrderRepository orderRepository;
    public Iterable<Order> getAllOrder(){return orderRepository.findAll();}
}
