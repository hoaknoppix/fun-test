package com.example.notificationsvc.entity;

import com.example.notificationsvc.dto.NotificationRequest;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
public class Notification {
  @Id
  private String id;
  private NotificationRequest notificationRequest;
  @CreatedBy
  private LocalDateTime createdBy;
}
