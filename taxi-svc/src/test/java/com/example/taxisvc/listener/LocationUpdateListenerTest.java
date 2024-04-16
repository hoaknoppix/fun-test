package com.example.taxisvc.listener;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.taxisvc.dto.TaxiMessage;
import com.example.taxisvc.entity.Taxi;
import com.example.taxisvc.repository.TaxiRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LocationUpdateListenerTest {
  @Mock
  private TaxiRepository taxiRepository;
  @Mock
  private ObjectMapper objectMapper;

  private LocationUpdateListener locationUpdateListener;

  @BeforeEach
  void setUp() {
    locationUpdateListener = new LocationUpdateListener(taxiRepository, objectMapper);
  }

  @Test
  void testListenMessageFromExistingTaxi() throws JsonProcessingException {
    TaxiMessage taxiMessage = new TaxiMessage();
    taxiMessage.setTaxiId("TAX-01");
    taxiMessage.setLongitude(1D);
    taxiMessage.setLatitude(10D);
    when(objectMapper.readValue("Test", TaxiMessage.class)).thenReturn(taxiMessage);
    when(taxiRepository.findByTaxiId("TAX-01")).thenReturn(Optional.of(Taxi.builder().build()));
    locationUpdateListener.listen("Test");
    verify(taxiRepository, times(1)).save(any());
  }

  @Test
  void testListenMessageFromNewTaxi() throws JsonProcessingException {
    TaxiMessage taxiMessage = new TaxiMessage();
    taxiMessage.setTaxiId("TAX-01");
    taxiMessage.setLongitude(1D);
    taxiMessage.setLatitude(10D);
    when(objectMapper.readValue("Test", TaxiMessage.class)).thenReturn(taxiMessage);
    when(taxiRepository.findByTaxiId("TAX-01")).thenReturn(Optional.empty());
    locationUpdateListener.listen("Test");
    verify(taxiRepository, times(1)).save(any());
  }
}
