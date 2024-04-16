package com.example.locationsvc.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.locationsvc.dto.LocationRequest;
import com.example.locationsvc.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LocationControllerTest {

  @Mock
  private LocationService locationService;
  private LocationController locationController;


  @BeforeEach
  void setUp() {
    locationController = new LocationController(locationService);
  }

  @Test
  void testUpdateLocationWithValidData() {
    LocationRequest locationRequest = new LocationRequest();
    locationRequest.setLatitude(1D);
    locationRequest.setLongitude(2D);
    locationRequest.setTaxiId("TAX-01");
    locationController.receiveLocation(locationRequest);
    verify(locationService, times(1)).handleLocation(locationRequest.getTaxiId(), locationRequest.getLatitude(), locationRequest.getLongitude());
  }

  @Test
  void testUpdateLocationWithInvalidData() {
    LocationRequest locationRequest = new LocationRequest();
    locationRequest.setLatitude(1D);
    locationRequest.setLongitude(2D);
    locationRequest.setTaxiId("TAX-01");
    doThrow(new RuntimeException("Test")).when(locationService).handleLocation(locationRequest.getTaxiId(), locationRequest.getLatitude(), locationRequest.getLongitude());
    try {
      locationController.receiveLocation(locationRequest);
      fail("RuntimeException must be thrown");
    } catch (RuntimeException exception) {
      assertThat(exception.getMessage()).isEqualTo("Test");
    }
  }
}
