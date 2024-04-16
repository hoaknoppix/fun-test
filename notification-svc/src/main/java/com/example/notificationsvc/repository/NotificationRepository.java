package com.example.notificationsvc.repository;

import com.example.notificationsvc.entity.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, String> {

}
