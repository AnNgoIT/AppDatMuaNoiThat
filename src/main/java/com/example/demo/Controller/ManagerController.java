package com.example.demo.Controller;


import com.example.demo.DAO.OrderDAO;
import com.example.demo.DAO.ProductDAO;
import com.example.demo.Entity.Order;
import com.example.demo.Entity.Product;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ManagerController {
    @Autowired
    ProductDAO productDAO;

    @Autowired
    OrderDAO orderDAO;
    @PostMapping("manager/updateQuantityProduct/processed/{productId}")
    public void updateCountProduct(@PathVariable("productId") Integer productId, @RequestParam("quantity") Long quantity){
        Optional<Product> product=productDAO.getProductById(productId);
        Product newProduct=product.get();
        newProduct.setQuantity(newProduct.getQuantity()- quantity);
        productDAO.saveProduct(newProduct);
    }
    @GetMapping("manager/orders")
    public Iterable<Order> getAllOrders(){
        return orderDAO.getAllOrders();
    }
    @GetMapping("manager/ordersByState")
    public Iterable<Order> getOrdersByState(@RequestParam("state") String state){
        return orderDAO.getOrdersByState(state);
    }
}
