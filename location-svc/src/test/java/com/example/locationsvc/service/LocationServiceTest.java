package com.example.locationsvc.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.locationsvc.entity.Taxi;
import com.example.locationsvc.exception.JsonParseException;
import com.example.locationsvc.repository.TaxiRepository;
import com.example.locationsvc.service.impl.LocationServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.kafka.core.KafkaTemplate;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

  private LocationService service;

  @Mock
  private TaxiRepository taxiRepository;

  @Mock
  private KafkaTemplate<String, String> kafkaTemplate;

  @Mock
  private NewTopic locationTopic;

  @Mock
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp(){
    service = new LocationServiceImpl(taxiRepository, kafkaTemplate, locationTopic, objectMapper);
  }

  @Test
  void testUpdateLocationForNewTaxi() throws JsonProcessingException {
    when(locationTopic.name()).thenReturn("TOPIC-01");
    when(objectMapper.writeValueAsString(any())).thenReturn("{}");
    when(taxiRepository.findById("TAXI-01")).thenReturn(Optional.empty());
    service.handleLocation("TAXI-01", 2D, 3D);
    verify(taxiRepository, times(1)).save(any());
    verify(kafkaTemplate, times(1)).send("TOPIC-01", "{}");
  }

  @Test
  void testUpdateLocationForNewTaxiWhenObjectMapperHasException() throws JsonProcessingException {
    when(locationTopic.name()).thenReturn("TOPIC-01");
    when(objectMapper.writeValueAsString(any())).thenThrow(new RuntimeException("Test"));
    when(taxiRepository.findById("TAXI-01")).thenReturn(Optional.empty());
    try {
      service.handleLocation("TAXI-01", 2D, 3D);
      fail("Exception must be thrown");
    } catch (JsonParseException jsonParseException) {
      assertThat(jsonParseException.getMessage()).isEqualTo("java.lang.RuntimeException: Test");
    }
  }
  @Test
  void testUpdateLocationForCurrentTaxi() throws JsonProcessingException {
    when(locationTopic.name()).thenReturn("TOPIC-01");
    when(objectMapper.writeValueAsString(any())).thenReturn("{}");
    Taxi taxi = Taxi.builder().taxiId("TAX-01").location(new GeoJsonPoint(2d, 4d)).build();
    when(taxiRepository.findById("TAXI-01")).thenReturn(Optional.of(taxi));
    service.handleLocation("TAXI-01", 2D, 3D);
    verify(taxiRepository, times(1)).save(taxi);
    verify(kafkaTemplate, times(1)).send("TOPIC-01", "{}");
  }

  @Test
  void testUpdateLocationForCurrentTaxiWhenObjectMapperHasException() throws JsonProcessingException {
    when(locationTopic.name()).thenReturn("TOPIC-01");
    when(objectMapper.writeValueAsString(any())).thenThrow(new RuntimeException("Test"));
    Taxi taxi = Taxi.builder().taxiId("TAX-01").location(new GeoJsonPoint(2d, 4d)).build();
    when(taxiRepository.findById("TAXI-01")).thenReturn(Optional.of(taxi));
    try {
      service.handleLocation("TAXI-01", 2D, 3D);
      fail("Exception must be thrown");
    } catch (JsonParseException jsonParseException) {
      assertThat(jsonParseException.getMessage()).isEqualTo("java.lang.RuntimeException: Test");
    }
  }


}
