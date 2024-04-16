package com.example.taxisvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaxiMessage {
  private String taxiId;
  private Double longitude;
  private Double latitude;
}
