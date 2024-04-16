package com.example.locationsvc.service.impl;

import com.example.locationsvc.dto.TaxiMessage;
import com.example.locationsvc.entity.Taxi;
import com.example.locationsvc.exception.JsonParseException;
import com.example.locationsvc.repository.TaxiRepository;
import com.example.locationsvc.service.LocationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

  private final TaxiRepository taxiRepository;

  private final KafkaTemplate<String, String> kafkaTemplate;

  private final NewTopic locationTopic;

  private final ObjectMapper objectMapper;

  @Override
  public void handleLocation(String taxiId, Double latitude, Double longitude) {
    taxiRepository.findById(taxiId).ifPresentOrElse(taxi -> updateLocation(latitude, longitude, taxi),
        () -> createTaxiEntity(taxiId, latitude, longitude));
  }

  private void createTaxiEntity(String taxiId, Double latitude, Double longitude) {
    try {
      Taxi taxi = Taxi.builder().taxiId(taxiId).location(new GeoJsonPoint(longitude, latitude)).build();
      taxiRepository.save(taxi);
      produceMessage(taxiId, latitude, longitude);
    } catch (Exception exception) {
      throw new JsonParseException(exception);
    }
  }

  private void produceMessage(String taxiId, Double latitude, Double longitude) throws JsonProcessingException {
    TaxiMessage taxiMessage = new TaxiMessage();
    taxiMessage.setTaxiId(taxiId);
    taxiMessage.setLongitude(longitude);
    taxiMessage.setLatitude(latitude);
    kafkaTemplate.send(locationTopic.name(), objectMapper.writeValueAsString(taxiMessage));
  }

  private void updateLocation(Double latitude, Double longitude, Taxi taxi) {
    try {
      taxi.setLocation(new GeoJsonPoint(longitude, latitude));
      taxiRepository.save(taxi);
      produceMessage(taxi.getTaxiId(), latitude, longitude);
    } catch (Exception exception) {
      throw new JsonParseException(exception);
    }
  }
}
