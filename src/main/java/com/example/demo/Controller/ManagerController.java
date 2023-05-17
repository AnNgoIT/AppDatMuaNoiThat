package com.example.demo.Controller;


import com.example.demo.DAO.OrderDAO;
import com.example.demo.DAO.ProductDAO;
import com.example.demo.Entity.Order;
import com.example.demo.Entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("manager/getAllOrdersProcessingOrState/{state1}+{state2}")
    public Iterable<Order> getAllOrdersProcessing(@PathVariable("state1") String state1,@PathVariable("state2") String state2){

        return orderDAO.getAllOrderProcessingOrState(state1, state2);
    }

    @RequestMapping("manager/changeStateOrder/{orderId}")
    public void changeStateOrder(@PathVariable("orderId") Long orderId) {
        Optional<Order> optionalOrder = orderDAO.findOrderByOrderId(orderId);

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            if (order.getState().equals("processing")) {
                order.setState("processed");
                order.setDate(new Date());
                orderDAO.saveOrder(order);
            }
        } else {
            throw new IllegalArgumentException("Invalid order ID");
        }
    }
    @DeleteMapping("manager/orderDelete/{orderId}")
    public void deleteOrder(@PathVariable("orderId") Integer orderId){
        orderDAO.deleteByOrderByOrderId(Integer.parseInt(orderId.toString()));
    }
}
