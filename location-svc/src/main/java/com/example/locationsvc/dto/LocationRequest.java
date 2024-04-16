package com.example.locationsvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocationRequest {
  private String taxiId;
  private Double latitude;
  private Double longitude;
}
