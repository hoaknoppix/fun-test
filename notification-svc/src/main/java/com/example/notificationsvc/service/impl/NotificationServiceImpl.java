package com.example.notificationsvc.service.impl;

import com.example.notificationsvc.dto.NotificationRequest;
import com.example.notificationsvc.entity.Notification;
import com.example.notificationsvc.repository.NotificationRepository;
import com.example.notificationsvc.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

  private final NotificationRepository notificationRepository;

  @Override
  public void send(NotificationRequest notificationRequest) {
    createNotification(notificationRequest);
  }

  private void createNotification(NotificationRequest notificationRequest) {
    Notification notification = Notification.builder().notificationRequest(notificationRequest).build();
    notificationRepository.save(notification);
    log.info("Sending to gcm or apns");
    //TODO: sending to gcm or apns
  }
}
