package com.example.locationsvc.controller;

import com.example.locationsvc.dto.LocationRequest;
import com.example.locationsvc.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LocationController {

  private final LocationService locationService;

  @PostMapping(value = "/location", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public void receiveLocation(@RequestBody LocationRequest locationRequest) {
    locationService.handleLocation(locationRequest.getTaxiId(), locationRequest.getLatitude(), locationRequest.getLongitude());
  }
}
