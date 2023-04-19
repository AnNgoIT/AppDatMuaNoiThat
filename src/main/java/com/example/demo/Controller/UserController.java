package com.example.demo.Controller;


import com.example.demo.DAO.CartDAO;
import com.example.demo.DAO.CategoryDAO;
import com.example.demo.DAO.UserDAO;
import com.example.demo.Entity.Cart;
import com.example.demo.Entity.Category;
import com.example.demo.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class UserController {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private CartDAO cartDAO;
    @Autowired
    private CategoryDAO categoryDAO;

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

}
