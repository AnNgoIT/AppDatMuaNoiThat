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
    private OrderDAO orderDAO;
    @Autowired
    private CategoryDAO categoryDAO;
    @Autowired
    private ProductDAO productDAO;

    @PostMapping("/user/signup")
    public User saveUser(@RequestBody User user){
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
    @RequestMapping("user/category/{id}")
    public Iterable<Product> getProductByCategory(@PathVariable("id") Integer categoryId){
        return productDAO.getProductByCategory(categoryDAO.getCategoryById(categoryId));
    }

    @RequestMapping("user/product/{id}")
    public Optional<Product> getProductById(@PathVariable("id") Integer productId){
        return productDAO.getProductById(productId);
    }

    @RequestMapping("user/products/{name}")
    public Iterable<Product> getProductByName(@PathVariable("name") String name){
        return productDAO.getProductByNameContaining(name);
    }

}
