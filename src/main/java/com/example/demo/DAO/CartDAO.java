package com.example.demo.DAO;

import com.example.demo.Entity.Cart;
import com.example.demo.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartDAO {
    @Autowired
    CartRepository cartRepository;
    public Cart save(Cart cart){
        return cartRepository.save(cart);
    }
}
