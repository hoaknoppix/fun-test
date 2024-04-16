package com.example.locationsvc.entity;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
public class Taxi {
  @Id
  private String taxiId;
  private GeoJsonPoint location;
  @LastModifiedBy
  private LocalDateTime lastModifiedBy;
  @CreatedBy
  private LocalDateTime createdBy;
}
