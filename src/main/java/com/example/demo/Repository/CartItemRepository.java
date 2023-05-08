package com.example.demo.Repository;

import com.example.demo.Entity.Cart;
import com.example.demo.Entity.CartItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem,Integer> {
    public Iterable<CartItem> findCartItemByCart(Optional<Cart> cart);
}
