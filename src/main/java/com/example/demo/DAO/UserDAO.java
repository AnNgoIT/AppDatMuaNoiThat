package com.example.demo.DAO;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDAO {
    @Autowired
    private UserRepository repository;
    public User get(User user){
        return repository.findUserByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

    public User save(User user){
        return repository.save(user);
    }

    public Iterable<User> findAll(){return repository.findAll();}
}

