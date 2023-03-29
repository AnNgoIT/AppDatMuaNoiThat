package com.example.demo.model.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
    public User findUserByUsernameAndPassword(String user,String pass);
}
