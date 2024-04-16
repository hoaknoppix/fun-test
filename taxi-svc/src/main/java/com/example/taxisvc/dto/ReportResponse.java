package com.example.taxisvc.dto;

import lombok.Data;

@Data
public class ReportResponse {
  private Integer numberOfAvailableTaxis;
  private Integer numberOfBookedTaxis;
}
