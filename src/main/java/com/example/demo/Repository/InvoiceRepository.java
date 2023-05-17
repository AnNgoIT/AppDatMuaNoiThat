package com.example.demo.Repository;

import com.example.demo.Entity.Invoice;
import com.example.demo.Entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends CrudRepository<Invoice,Integer> {
    @Override
    void delete(Invoice entity);

    public Invoice findInvoiceByOrder(Optional<Order> order);
    void deleteByOrder_OrderId(Integer orderId);
}
