package com.example.notificationsvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotificationRequest {
  private String bookingId;
  private Double latitude;
  private Double longitude;
}
