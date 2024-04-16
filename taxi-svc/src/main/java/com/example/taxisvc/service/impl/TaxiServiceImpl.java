package com.example.taxisvc.service.impl;

import com.example.taxisvc.client.NotificationClient;
import com.example.taxisvc.client.dto.NotificationRequest;
import com.example.taxisvc.dto.BookingRequest;
import com.example.taxisvc.dto.ReportResponse;
import com.example.taxisvc.dto.TaxiResponse;
import com.example.taxisvc.dto.UpdateTaxiRequest;
import com.example.taxisvc.entity.Booking;
import com.example.taxisvc.entity.State;
import com.example.taxisvc.entity.Taxi;
import com.example.taxisvc.repository.BookingRepository;
import com.example.taxisvc.repository.TaxiRepository;
import com.example.taxisvc.service.TaxiService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaxiServiceImpl implements TaxiService {

  private final TaxiRepository taxiRepository;

  private final NotificationClient notificationClient;
  private final BookingRepository bookingRepository;

  @Override
  public List<TaxiResponse> getTaxis() {
    return taxiRepository.findAll().stream().map(taxi -> {
      TaxiResponse taxiResponse = new TaxiResponse();
      taxiResponse.setLocation(taxi.getLocation());
      taxiResponse.setTaxiId(taxi.getTaxiId());
      taxiResponse.setCreatedBy(taxi.getCreatedBy());
      taxiResponse.setLastModifiedBy(taxi.getLastModifiedBy());
      taxiResponse.setState(taxi.getState());
      return taxiResponse;
    }).toList();
  }

  @Override
  public ReportResponse getReport() {
    Integer numberOfAvailable = taxiRepository.countTaxisByState(State.AVAILABLE);
    Integer numberOfBooked = taxiRepository.countTaxisByState(State.BOOKED);
    ReportResponse response = new ReportResponse();
    response.setNumberOfAvailableTaxis(numberOfAvailable);
    response.setNumberOfBookedTaxis(numberOfBooked);
    return response;
  }

  @Override
  public void update(String taxiId, UpdateTaxiRequest updateTaxiRequest) {
    Taxi taxi = taxiRepository.findByTaxiId(taxiId).orElseThrow();
    taxi.setState(updateTaxiRequest.getState());
    taxiRepository.save(taxi);
  }

  @Override
  public void book(BookingRequest bookingRequest) {
    Booking booking = Booking.builder()
        .location(new GeoJsonPoint(bookingRequest.getLongitude(), bookingRequest.getLatitude())).build();
    final Booking savedBooking = bookingRepository.save(booking);
    //TODO: do spatial query to query the booked taxi that is going to go the nearby location
    taxiRepository.findAllByState(State.AVAILABLE).forEach(taxi -> {
      NotificationRequest request = new NotificationRequest();
      request.setBookingId(savedBooking.getBookingId());
      request.setLongitude(savedBooking.getLocation().getX());
      request.setLatitude(savedBooking.getLocation().getY());
      request.setTaxiId(taxi.getTaxiId());
      notificationClient.send(request);
    });

  }
}
