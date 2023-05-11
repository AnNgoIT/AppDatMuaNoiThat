package com.example.demo.Controller;


import com.example.demo.DAO.*;
import com.example.demo.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    //get product in cart
    @RequestMapping("user/order/inCart/{id}")
    public ArrayList<Product> getProductInCartByUser(@PathVariable("id") Integer userId){
        Optional<User> user=userDAO.findUserById(userId);
        ArrayList<Order> orders= orderDAO.getOrderByUser(user);
        ArrayList<Product> products=new ArrayList<>();
        for (int i=0;i<orders.size();i++){
            if (orders.get(i).getState().equals("inCart"))
                products.add(orders.get(i).getProduct());
        }
        return products;
    }
    //get count product in cart
    @RequestMapping("user/order/countProduct/inCart/{id}")
    public ArrayList<Long> getCountProductInCart(@PathVariable("id")Integer userId){
        Optional<User> user=userDAO.findUserById(userId);
        ArrayList<Order> orders= orderDAO.getOrderByUser(user);
        ArrayList<Long> counts=new ArrayList<Long>();
        for (int i=0;i<orders.size();i++){
            if (orders.get(i).getState().equals("inCart"))
                counts.add(orders.get(i).getCount());
        }
        return counts;
    }

    //get product processing
    @RequestMapping("user/order/processing/{id}")
    public ArrayList<Product> getProductProcessingByUser(@PathVariable("id") Integer userId){
        Optional<User> user=userDAO.findUserById(userId);
        ArrayList<Order> orders= orderDAO.getOrderByUser(user);
        ArrayList<Product> products=new ArrayList<>();
        for (int i=0;i<orders.size();i++){
            if (orders.get(i).getState().equals("processing"))
                products.add(orders.get(i).getProduct());
        }
        return products;
    }

    //get count product processing
    @RequestMapping("user/order/countProduct/processing/{id}")
    public ArrayList<Long> getCountProductProcessing(@PathVariable("id")Integer userId){
        Optional<User> user=userDAO.findUserById(userId);
        ArrayList<Order> orders= orderDAO.getOrderByUser(user);
        ArrayList<Long> counts=new ArrayList<Long>();
        for (int i=0;i<orders.size();i++){
            if (orders.get(i).getState().equals("processing"))
                counts.add(orders.get(i).getCount());
        }
        return counts;
    }

    @RequestMapping("user/order/processed/{id}")
    public ArrayList<Product> getProductProcessedByUser(@PathVariable("id") Integer userId){
        Optional<User> user=userDAO.findUserById(userId);
        ArrayList<Order> orders= orderDAO.getOrderByUser(user);
        ArrayList<Product> products=new ArrayList<>();
        for (int i=0;i<orders.size();i++){
            if (orders.get(i).getState().equals("processed"))
                products.add(orders.get(i).getProduct());
        }
        return products;
    }

}
