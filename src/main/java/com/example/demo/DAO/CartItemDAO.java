package com.example.demo.DAO;

import com.example.demo.Entity.Cart;
import com.example.demo.Entity.CartItem;
import com.example.demo.Repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemDAO {
    @Autowired
    CartItemRepository cartItemRepository;
    public Iterable<CartItem> getCartItemByCart(Optional<Cart> cart){
        return cartItemRepository.findCartItemByCart(cart);
    }
}
