package com.example.taxisvc.client;

import com.example.taxisvc.client.dto.NotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "notificationClient", url = "http://localhost:9080")
public interface NotificationClient {
  @PostMapping(value = "/notification", produces = "application/json", consumes = "application/json")
  void send(NotificationRequest notificationRequest);
}
