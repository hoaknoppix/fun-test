package com.example.taxisvc.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.taxisvc.client.NotificationClient;
import com.example.taxisvc.dto.BookingRequest;
import com.example.taxisvc.dto.ReportResponse;
import com.example.taxisvc.dto.TaxiResponse;
import com.example.taxisvc.dto.UpdateTaxiRequest;
import com.example.taxisvc.entity.Booking;
import com.example.taxisvc.entity.State;
import com.example.taxisvc.entity.Taxi;
import com.example.taxisvc.repository.BookingRepository;
import com.example.taxisvc.repository.TaxiRepository;
import com.example.taxisvc.service.impl.TaxiServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

@ExtendWith(MockitoExtension.class)
class TaxiServiceTest {

  private TaxiService service;

  @Mock
  private TaxiRepository taxiRepository;

  @Mock
  private BookingRepository bookingRepository;

  @Mock
  private NotificationClient notificationClient;

  @BeforeEach
  void setUp(){
    service = new TaxiServiceImpl(taxiRepository, notificationClient, bookingRepository);
  }

  @Test
  void testGetTaxis() {
    Taxi taxi = Taxi.builder().taxiId("TEST-01").state(State.AVAILABLE).location(new GeoJsonPoint(1D, 2D)).build();
    when(taxiRepository.findAll()).thenReturn(List.of(taxi));
    List<TaxiResponse> taxis = service.getTaxis();
    assertThat(taxis).hasSize(1);
    TaxiResponse taxiResponse = taxis.get(0);
    assertThat(taxiResponse.getTaxiId()).isEqualTo(taxi.getTaxiId());
    assertThat(taxiResponse.getLocation()).isEqualTo(taxi.getLocation());
    assertThat(taxiResponse.getState()).isEqualTo(taxi.getState());
  }

  @Test
  void testGetReport() {
    when(taxiRepository.countTaxisByState(State.AVAILABLE)).thenReturn(2);
    when(taxiRepository.countTaxisByState(State.BOOKED)).thenReturn(3);
    ReportResponse response = service.getReport();
    assertThat(response.getNumberOfAvailableTaxis()).isEqualTo(2);
    assertThat(response.getNumberOfBookedTaxis()).isEqualTo(3);
  }

  @Test
  void testUpdateStateWithExistingId() {
    Taxi taxi = Taxi.builder().state(State.AVAILABLE).build();
    when(taxiRepository.findByTaxiId("Tax-01")).thenReturn(Optional.of(taxi));
    UpdateTaxiRequest updateTaxiRequest = new UpdateTaxiRequest();
    updateTaxiRequest.setState(State.BOOKED);
    service.update("Tax-01", updateTaxiRequest);
    ArgumentCaptor<Taxi> taxiArgumentCaptor = ArgumentCaptor.forClass(Taxi.class);
    verify(taxiRepository,times(1)).save(taxiArgumentCaptor.capture());
    Taxi updatedTaxi = taxiArgumentCaptor.getValue();
    assertThat(updatedTaxi.getState()).isEqualTo(updateTaxiRequest.getState());
  }

  @Test
  void testBook() {
    BookingRequest bookingRequest = new BookingRequest();
    bookingRequest.setLatitude(1D);
    bookingRequest.setLongitude(2D);
    when(taxiRepository.findAllByState(State.AVAILABLE)).thenReturn(
        List.of(Taxi.builder().build(), Taxi.builder().build()));
    when(bookingRepository.save(any())).thenReturn(Booking.builder().location(new GeoJsonPoint(1.3D, 2.3D)).build());
    service.book(bookingRequest);
    verify(bookingRepository, times(1)).save(any());
    verify(notificationClient,times(2)).send(any());
  }

}
