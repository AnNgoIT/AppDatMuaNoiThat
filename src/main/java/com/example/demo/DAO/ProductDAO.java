package com.example.demo.DAO;

import com.example.demo.Entity.Product;
import com.example.demo.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductDAO {
    @Autowired
    ProductRepository productRepository;
    public Iterable<Product> getProduct(){return productRepository.findAll();};

    public Optional<Product> getProductById(Integer productId){
        return productRepository.findById(productId);
    }
}
