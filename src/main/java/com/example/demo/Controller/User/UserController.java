package com.example.demo.Controller.User;

import com.example.demo.model.User.User;
import com.example.demo.model.User.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserDAO userDAO;
    @PostMapping("/user/get")
    public User getUser(@RequestBody  User user){
        return userDAO.get(user);
    }

    @PostMapping("/user/signup")
    public User save(@RequestBody  User user){
        return userDAO.save(user);
    }

}
