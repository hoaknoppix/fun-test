package com.example.notificationsvc.controller;

import com.example.notificationsvc.dto.NotificationRequest;
import com.example.notificationsvc.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationController {

  private final NotificationService notificationService;

  @PostMapping(value = "/notification", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public void send(@RequestBody NotificationRequest notificationRequest) {
    notificationService.send(notificationRequest);
  }
}
