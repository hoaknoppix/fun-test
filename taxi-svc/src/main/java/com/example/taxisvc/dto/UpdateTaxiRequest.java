package com.example.taxisvc.dto;

import com.example.taxisvc.entity.State;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateTaxiRequest {
  private State state;
}
