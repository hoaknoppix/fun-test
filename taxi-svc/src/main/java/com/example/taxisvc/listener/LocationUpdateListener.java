package com.example.taxisvc.listener;

import com.example.taxisvc.dto.TaxiMessage;
import com.example.taxisvc.entity.State;
import com.example.taxisvc.entity.Taxi;
import com.example.taxisvc.repository.TaxiRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LocationUpdateListener {

  private final TaxiRepository taxiRepository;
  private final ObjectMapper objectMapper;

  @KafkaListener(topics = "location-update", groupId = "1")
  public void listen(String message) throws JsonProcessingException {
    log.info("Receiving message {}", message);
    TaxiMessage taxiMessage = objectMapper.readValue(message, TaxiMessage.class);
    taxiRepository.findByTaxiId(taxiMessage.getTaxiId())
        .ifPresentOrElse(entity -> updateEntity(entity, taxiMessage), () -> createEntity(taxiMessage));
  }

  private void createEntity(TaxiMessage taxiMessage) {
    Taxi taxi = Taxi.builder().taxiId(taxiMessage.getTaxiId())
        .location(new GeoJsonPoint(taxiMessage.getLongitude(), taxiMessage.getLatitude()))
        .state(State.AVAILABLE).build();
    taxiRepository.save(taxi);
  }

  private void updateEntity(Taxi taxi, TaxiMessage taxiMessage) {
    taxi.setLocation(new GeoJsonPoint(taxiMessage.getLongitude(), taxiMessage.getLatitude()));
    taxiRepository.save(taxi);
  }
}
