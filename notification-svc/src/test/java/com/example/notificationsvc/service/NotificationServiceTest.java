package com.example.notificationsvc.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.notificationsvc.dto.NotificationRequest;
import com.example.notificationsvc.repository.NotificationRepository;
import com.example.notificationsvc.service.impl.NotificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

  private NotificationService service;

  @Mock
  private NotificationRepository notificationRepository;

  @BeforeEach
  void setUp(){
    service = new NotificationServiceImpl(notificationRepository);
  }

  @Test
  void testSaveNotification() {
    service.send(new NotificationRequest());
    verify(notificationRepository, times(1)).save(any());
  }

}
