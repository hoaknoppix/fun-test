package com.example.taxisvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookingRequest {
  private Double longitude;
  private Double latitude;
}
