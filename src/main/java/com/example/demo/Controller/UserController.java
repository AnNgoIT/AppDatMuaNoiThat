package com.example.demo.Controller;


import com.example.demo.DAO.*;
import com.example.demo.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    private NotificationDAO notificationDAO;

    @PostMapping("/user/signup")
    public User saveUser(@RequestBody User user){
        user.setRole("customer");
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
    @RequestMapping("user/productname/{name}")
    public Optional<Product> getOneProductByName(@PathVariable("name") String name){
        return productDAO.getProductByName(name);
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


    //setting user
    @RequestMapping("user/setting/{id}")
    public User saveSettingUser(@PathVariable("id") Integer userId,@RequestParam("name") String name,@RequestParam("password") String password,
                                @RequestParam("address") String address,@RequestParam("address2") String address2,@RequestParam("address3") String address3){
        Optional<User> newUser = userDAO.findUserById(userId);
        User user=newUser.get();
        user.setName(name);
        user.setPassword(password);
        user.setAddress(address);
        user.setAddress2(address2);
        user.setAddress3(address3);
        return userDAO.saveUserSetting(user);
    }

    @RequestMapping("user/getUser/{id}")
    public Optional<User> getUser (@PathVariable("id")Integer userId){
        return  userDAO.findUserById(userId);
    }

    //add to cart
    @RequestMapping("user/addtocart/{userId}/{productId}")
    public Order addToCart(@PathVariable("userId")Integer userId,@PathVariable("productId")Integer productId,@RequestParam("count") Long count){
        Optional<User> user=userDAO.findUserById(userId);
        Optional<Product> product=productDAO.getProductById(productId);
        Order order=orderDAO.findOrderByProductAndUserAndState(product,user,"inCart");
        if (order == null){
            User newUser = user.get();
            Product newProduct=product.get();
            Order newOrder =new Order();
            newOrder.setUser(newUser);
            newOrder.setProduct(newProduct);
            newOrder.setCount(count);
            newOrder.setState("inCart");
            return orderDAO.saveOrder(newOrder);
        }
        order.setCount(order.getCount()+count);
        return orderDAO.saveOrder(order);
    }
    //update cart
    @RequestMapping("user/updateCart/{userId}/{productId}")
    public Order updateCart(@PathVariable("userId")Integer userId,@PathVariable("productId")Integer productId,@RequestParam("count") Long count){
        Optional<User> user=userDAO.findUserById(userId);
        Optional<Product> product=productDAO.getProductById(productId);
        Order order=orderDAO.findOrderByProductAndUserAndState(product,user,"inCart");
        order.setCount(count);
        return orderDAO.saveOrder(order);
    }

    //Thanh toán
    @RequestMapping("user/paying/{userId}")
    public void paying(@PathVariable("userId") Integer userId,@RequestParam("address") String address){
        Optional<User> user=userDAO.findUserById(userId);
        ArrayList<Order> order=orderDAO.getOrderByUser(user);
        for (int i=0;i<order.size();i++) {
            if (order.get(i).getState().equals("inCart")) {
                order.get(i).setState("processing");
                order.get(i).setDate(new Date());
                order.get(i).setAddress(address);
                orderDAO.saveOrder(order.get(i));
            }
        }

    }


    //Delete Order
    @DeleteMapping("user/orderDelete/{productId}/{userId}")
    public void deleteOrderByProductAndUser(@PathVariable("productId")Integer productId,@PathVariable("userId")Integer userId){
        Optional<Product> product=productDAO.getProductById(productId);
        Optional<User> user=userDAO.findUserById(userId);
        Order order=orderDAO.findOrderByProductAndUserAndState(product,user,"inCart");
        orderDAO.deleteByOrderByProductAndUser(Integer.parseInt(order.getOrderId().toString()));
    }


    @GetMapping("user/getOrderInCartByProductAndUser/{productId}/{userId}")
    public Order getOrderInCartByProductAndUser(@PathVariable("productId")Integer productId,@PathVariable("userId")Integer userId){
        Optional<Product> product=productDAO.getProductById(productId);
        Optional<User> user=userDAO.findUserById(userId);
        return orderDAO.findOrderByProductAndUserAndState(product,user,"inCart");
    }

    //notification
    @GetMapping("user/notification/{userId}")
    public ArrayList<Notification> getNotification(@PathVariable("userId")Integer userId){
        Optional<User> user=userDAO.findUserById(userId);
        return notificationDAO.findNotificationByUser(user);
    }

    @PostMapping("user/saveNotification/{userId}")
    public void saveNotification(@PathVariable("userId")Integer userId,@RequestParam("description") String description,
                                 @RequestParam("orderId") Integer orderId,@RequestParam("state") String state){
        Notification newNotification=new Notification();
        newNotification.setDescription(description);
        Optional<User> user=userDAO.findUserById(userId);
        newNotification.setUser(user.get());
        newNotification.setOrder(orderDAO.findOrderById(orderId).get());
        newNotification.setState(state);
        notificationDAO.saveNotification(newNotification);
    }
    @GetMapping("user/notificationOrder/{userId}")
    public ArrayList<Long> getOrderByNotification(@PathVariable("userId")Integer userId){
        Optional<User> user=userDAO.findUserById(userId);
        ArrayList<Notification> notification=notificationDAO.findNotificationByUser(user);
        ArrayList<Long> orderIdList=new ArrayList<>();
        for (int i=0;i<notification.size();i++){
            orderIdList.add(notification.get(i).getOrder().getOrderId());
        }
        return orderIdList;
    }
    @GetMapping("user/productByOrders")
    public ArrayList<Product> getProductByOrder(@RequestParam("listOrder")ArrayList<Integer> orderIdList) {
        ArrayList<Product> productArrayList=new ArrayList<>();
        for (int i = 0; i < orderIdList.size(); i++){
            Optional<Order> order=orderDAO.findOrderById(orderIdList.get(i));
            productArrayList.add(order.get().getProduct());
        }
        return productArrayList;
    }
    @GetMapping("user/getCountOrders")
    public ArrayList<Long> getCountOrder(@RequestParam("listOrder")ArrayList<Integer> orderIdList){
        ArrayList<Long> countList=new ArrayList<>();
        for (int i = 0; i < orderIdList.size(); i++){
            Optional<Order> order=orderDAO.findOrderById(orderIdList.get(i));
            countList.add(order.get().getCount());
        }
        return countList;
    }
    @GetMapping("user/getOrderInCart/{userId}")
    public ArrayList<Long> getOrderById(@PathVariable("userId")Integer userId){
        ArrayList<Long> orderId=new ArrayList<>();
        ArrayList<Order> orderArrayList=orderDAO.getOrderByUserAndState(userDAO.findUserById(userId),"inCart");
        for (int i =0;i<orderArrayList.size();i++){
            orderId.add(orderArrayList.get(i).getOrderId());
        }
        return orderId;
    }

    @PostMapping("user/saveNotificationHide/{notificationId}")
    public void saveNotificationById(@PathVariable("notificationId")Integer notificationId){
        Notification notification=notificationDAO.findByID(notificationId).get();
        notification.setState("hide");
        notificationDAO.saveNotification(notification);
    }
}
