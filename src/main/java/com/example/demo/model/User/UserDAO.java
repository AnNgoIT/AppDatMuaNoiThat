package com.example.demo.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
}
