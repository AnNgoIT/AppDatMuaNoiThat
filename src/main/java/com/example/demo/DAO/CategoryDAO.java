package com.example.demo.DAO;

import com.example.demo.Entity.Cart;
import com.example.demo.Entity.Category;
import com.example.demo.Repository.CartRepository;
import com.example.demo.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryDAO {
    @Autowired
    CategoryRepository categoryRepository;
    public Iterable<Category> getAll(){
        return categoryRepository.findAll();
    }
}