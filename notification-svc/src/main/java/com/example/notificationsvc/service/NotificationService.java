package com.example.notificationsvc.service;

import com.example.notificationsvc.dto.NotificationRequest;

public interface NotificationService {
    void send(NotificationRequest notificationRequest);
}
