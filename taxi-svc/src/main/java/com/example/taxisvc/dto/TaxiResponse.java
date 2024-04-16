package com.example.taxisvc.dto;

import com.example.taxisvc.entity.State;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

@Data
public class TaxiResponse {
  private String taxiId;
  private GeoJsonPoint location;
  @LastModifiedBy
  private LocalDateTime lastModifiedBy;
  @CreatedBy
  private LocalDateTime createdBy;
  private State state;
}
