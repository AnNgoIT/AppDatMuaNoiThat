package com.example.demo.Controller;
import com.example.demo.DAO.CategoryDAO;
import com.example.demo.DAO.OrderDAO;
import com.example.demo.DAO.ProductDAO;
import com.example.demo.Entity.Category;
import com.example.demo.DAO.InvoiceDao;
import com.example.demo.DAO.NotificationDAO;
import com.example.demo.Entity.Invoice;
import com.example.demo.Entity.Notification;
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

    @Autowired

    private CategoryDAO categoryDAO;

    @GetMapping("manager/countProductByCategory")
        public ArrayList<Integer> getCountProductByCategory()
    {
        ArrayList<Integer> count = new ArrayList<>();
        Iterable<Category> categories=categoryDAO.getAll();
        for (Category category: categories) {
            ArrayList<Product> products = (ArrayList<Product>) productDAO.getProductByCategory(Optional.of(category));
            int quantity_product = products.size();
            count.add(quantity_product);
        }
        return count;
    }

    @GetMapping("manager/countProductByCategoryId/{categoryId}")
    public int getCountProductByCategoryId(@PathVariable("categoryId") Integer categoryId)
    {
        Optional<Category> category = categoryDAO.getCategoryById(categoryId);
        ArrayList<Product> products = (ArrayList<Product>) productDAO.getProductByCategory(category);
        int count = products.size();
        return count;
    }

    InvoiceDao invoiceDAO;

    @Autowired
    NotificationDAO notificationDAO;

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
        ArrayList<Order> orderList = orderDAO.getOrdersByState("processed");
        Long totalRevenue = Long.valueOf(0);
        for(Order order : orderList){
            totalRevenue += order.getProduct().getPrice() * order.getCount();
        }
        return totalRevenue;
    }@GetMapping("manager/revenueByDate")

    public Long getTotalRevenueByDate(@RequestParam("date") String date) {

        ArrayList<Order> orderList = orderDAO.getOrdersByState("processed");
        Long totalRevenue = Long.valueOf(0);
        for (Order order : orderList) {
            Date orderDate = order.getDate();
            LocalDate localDate = orderDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedOrderDate = localDate.format(dateTimeFormatter);
            if (formattedOrderDate.equals(date)) {
                totalRevenue += order.getProduct().getPrice() * order.getCount();
            }
        }
        return totalRevenue;
    }
    @GetMapping("manager/totalProcessed")
    public int getTotalProcessed(){
        ArrayList<Order> orderArrayList=orderDAO.getOrdersByState("processed");
        return orderArrayList.size();
    }
    @GetMapping("manager/totalProcessing")
    public int getTotalProcessing(){
        ArrayList<Order> orderArrayList=orderDAO.getOrdersByState("processing");
        return orderArrayList.size();
    }

    @GetMapping("manager/getTotalDay")
    public Long getTotalDay(@RequestParam("day") String day){
        ArrayList<Order> orderList = orderDAO.getOrdersByState("processed");
        Long total =Long.valueOf(0);
        for(Order order:orderList){
            if (!(order.getDate() == null)){
                Date input = order.getDate();
                LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd");
                String formattedDate = date.format(dateTimeFormatter);
                if(formattedDate.equals(day)){
                    total += order.getCount()*order.getProduct().getPrice();
                }
            }

        }
        return total;
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
                Invoice invoice = new Invoice();
                invoice.setOrder(order);
                // Thiết lập thông tin khác cho Invoice (nếu cần)

                invoiceDAO.saveInvoice(invoice);
            }
        } else {
            throw new IllegalArgumentException("Invalid order ID");
        }
    }

    @DeleteMapping("manager/orderDelete/{orderId}")
    public void deleteOrder(@PathVariable("orderId") Integer orderId){
        Optional<Order> order = orderDAO.findOrderById(orderId);
        //Xóa references
        Notification notification = notificationDAO.findByOrderOrderId(order);
        notification.setOrder(null);
        notification.setUser(null);
        notificationDAO.delete(notification);
        if (order.get().getState().equals("processed") ){
            Invoice invoice = invoiceDAO.findInvoiceByOrder(order);
            invoice.setOrder(null);
            invoiceDAO.delete(invoice);
        }
        orderDAO.deleteByOrderByOrderId(Integer.parseInt(orderId.toString()));
    }
}
