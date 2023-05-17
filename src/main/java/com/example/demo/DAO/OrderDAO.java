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
    public ArrayList<Order> getOrderByUser(Optional<User> user){return orderRepository.findOrdersByUser(user);}

    public Order saveOrder(Order order){return orderRepository.save(order);}

    public Optional<Order> findOrderById(Integer orderId){return orderRepository.findById(orderId);}

    public Order findOrderByProductAndUserAndState(Optional<Product> product,Optional<User>user,String state){return orderRepository.findOrderByProductAndUserAndState(product,user,state);}

    public void deleteByOrderByProductAndUser(Integer orderId){orderRepository.deleteById(orderId);}

    public ArrayList<Order> getOrderByUserAndState(Optional<User> user,String state){return orderRepository.findOrdersByUserAndState(user,state);}
    public Iterable<Order> getAllOrders(){
        return orderRepository.findAll();
    }
    public ArrayList<Order> getOrdersByState(String state){
        return orderRepository.findOrdersByState(state);
    }

    public Iterable<Order> getAllOrderProcessingOrState(String state1, String state2){
        return orderRepository.findAllByStateOrState(state1, state2);
    }
    public Optional<Order> findOrderByOrderId (Long orderId){return orderRepository.findOrOrderByOrderId(orderId);}
    public void deleteByOrderByOrderId(Integer orderId){orderRepository.deleteById(orderId);}
}
