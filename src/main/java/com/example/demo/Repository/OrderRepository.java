package com.example.demo.Repository;

import com.example.demo.Entity.Order;
import com.example.demo.Entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order,Integer> {
    public ArrayList<Order> findOrderByUser(Optional<User> user);
}
