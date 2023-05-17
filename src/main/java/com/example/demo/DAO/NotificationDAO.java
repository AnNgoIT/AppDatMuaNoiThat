package com.example.demo.DAO;

import com.example.demo.Entity.Notification;
import com.example.demo.Entity.Order;
import com.example.demo.Entity.User;
import com.example.demo.Repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class NotificationDAO {
    @Autowired
    NotificationRepository notificationRepository;
    public Notification saveNotification(Notification notification){return notificationRepository.save(notification);}
    public ArrayList<Notification> findNotificationByUser(Optional<User> user){return notificationRepository.findNotificationsByUser(user);}

    public Optional<Notification> findByID(Integer notificationId){return notificationRepository.findById(notificationId);}

    public void delete(Notification notification){notificationRepository.delete(notification);}

    public Notification findByOrderOrderId(Optional<Order> order){return notificationRepository.findNotificationByOrder(order);}
}
