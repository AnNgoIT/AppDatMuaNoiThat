package com.example.demo.DAO;

import com.example.demo.Entity.Order;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.User;
import com.example.demo.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class OrderDAO {
    @Autowired
    OrderRepository orderRepository;
    public ArrayList<Order> getOrderByUser(Optional<User> user){return orderRepository.findOrderByUser(user);}

    public Order saveOrder(Order order){return orderRepository.save(order);}

    public Optional<Order> findOrderById(Integer orderId){return orderRepository.findById(orderId);}

    public Order findOrderByProductAndUser(Optional<Product> product,Optional<User>user){return orderRepository.findOrderByProductAndUser(product,user);}

    public void deleteByOrderByProductAndUser(Integer orderId){orderRepository.deleteById(orderId);}
}
