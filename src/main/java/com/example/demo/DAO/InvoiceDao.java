package com.example.demo.DAO;

import com.example.demo.Entity.Invoice;
import com.example.demo.Entity.Order;
import com.example.demo.Repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InvoiceDao {
    @Autowired
    InvoiceRepository invoiceRepository;

    public Invoice saveInvoice(Invoice invoice){return invoiceRepository.save(invoice);}

    public Invoice findInvoiceByOrder(Optional<Order> order){return invoiceRepository.findInvoiceByOrder(order);}

    public void delete(Invoice invoice){invoiceRepository.delete(invoice);}
}
