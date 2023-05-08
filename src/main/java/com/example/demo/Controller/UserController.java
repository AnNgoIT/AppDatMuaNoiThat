package com.example.demo.Controller;


import com.example.demo.DAO.*;
import com.example.demo.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private CartDAO cartDAO;
    @Autowired
    private CategoryDAO categoryDAO;
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private CartItemDAO cartItemDAO;

    @PostMapping("/user/signup")
    public User saveUser(@RequestBody User user){
        Cart cart=new Cart();
        cart.setUser(user);
        cartDAO.save(cart);
        return userDAO.save(user);
    }

    @PostMapping("/user/login")
    public User getUser(@RequestBody User user){
        return userDAO.get(user);
    }

    @RequestMapping("user/categories")
    public Iterable<Category> getCategory(){
        return categoryDAO.getAll();
    }

    @RequestMapping("user/products")
    public Iterable<Product> getProduct(){
        return productDAO.getProduct();
    }

    @GetMapping("user/products/{id}")
    public Iterable<Product> getProductByCategory(@PathVariable("id") Integer categoryId){
        return productDAO.getProductByCategory(categoryDAO.getCategoryById(categoryId));
    }
    @GetMapping("user/cart/{id}")
    public Optional<Cart> getProductInCart(@PathVariable("id") Integer cartId){
       return cartDAO.getCartById(cartId);
    }
}
