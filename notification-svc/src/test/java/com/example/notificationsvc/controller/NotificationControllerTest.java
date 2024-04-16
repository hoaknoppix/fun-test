package com.example.notificationsvc.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.notificationsvc.dto.NotificationRequest;
import com.example.notificationsvc.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

  @Mock
  private NotificationService notificationService;
  private NotificationController notificationController;


  @BeforeEach
  void setUp() {
    notificationController = new NotificationController(notificationService);
  }

  @Test
  void testSendNotificationWithValidData() {
    NotificationRequest notificationRequest = new NotificationRequest();
    notificationRequest.setLatitude(1D);
    notificationRequest.setLongitude(2D);
    notificationRequest.setBookingId("BOK-01");
    notificationController.send(notificationRequest);
    verify(notificationService, times(1)).send(notificationRequest);
  }

  @Test
  void testSendNotificationWithInvalidData() {
    NotificationRequest notificationRequest = new NotificationRequest();
    notificationRequest.setLatitude(1D);
    notificationRequest.setLongitude(2D);
    notificationRequest.setBookingId("BOK-01");
    doThrow(new RuntimeException("Test")).when(notificationService).send(notificationRequest);
    try {
      notificationController.send(notificationRequest);
      fail("RuntimeException must be thrown");
    } catch (RuntimeException exception) {
      assertThat(exception.getMessage()).isEqualTo("Test");
    }
  }
}
