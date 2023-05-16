package com.example.demo.Repository;

import com.example.demo.Entity.Notification;
import com.example.demo.Entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface NotificationRepository extends CrudRepository<Notification,Integer> {
    public ArrayList<Notification> findNotificationsByUser(Optional<User> user);
}
