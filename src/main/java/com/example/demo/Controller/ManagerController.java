package com.example.demo.Controller;


import com.example.demo.DAO.OrderDAO;
import com.example.demo.DAO.ProductDAO;
import com.example.demo.Entity.Order;
import com.example.demo.Entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
    public ArrayList<Order> getOrdersByState(@RequestParam("state") String state){
        return orderDAO.getOrdersByState(state);
    }
    @GetMapping("manager/revenue")
    public Long getTotalRevenue(){
        ArrayList<Order> orderList = orderDAO.getOrdersByState("processing");
        Long totalRevenue = Long.valueOf(0);
        for(Order order : orderList){
            totalRevenue += order.getProduct().getPrice() * order.getCount();
        }
        return totalRevenue;
    }
    @GetMapping("manager/revenueByDate")
    public Long getTotalRevenueByDate(@RequestParam("date") String date){

        ArrayList<Order> orderList = orderDAO.getOrdersByState("processing");
        Long totalRevenue = Long.valueOf(0);
        for(Order order : orderList){
            Date orderDate = order.getDate();
            LocalDate localDate = orderDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedOrderDate = localDate.format(dateTimeFormatter);
            if(formattedOrderDate.equals(date)){
                totalRevenue += order.getProduct().getPrice() * order.getCount();
            }
        }
        return totalRevenue;
    }
}
