package com.example.taxisvc.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.taxisvc.dto.BookingRequest;
import com.example.taxisvc.dto.ReportResponse;
import com.example.taxisvc.dto.TaxiResponse;
import com.example.taxisvc.dto.UpdateTaxiRequest;
import com.example.taxisvc.entity.State;
import com.example.taxisvc.service.TaxiService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaxiControllerTest {

  @Mock
  private TaxiService taxiService;
  private TaxiController taxiController;


  @BeforeEach
  void setUp() {
    taxiController = new TaxiController(taxiService);
  }

  @Test
  void testGetTaxis() {
    List<TaxiResponse> expectedResponse = List.of(new TaxiResponse());
    when(taxiService.getTaxis()).thenReturn(expectedResponse);
    List<TaxiResponse> response = taxiController.getTaxis();
    assertThat(response).isEqualTo(expectedResponse);
  }

  @Test
  void testGetReport() {
    ReportResponse expectedResponse = new ReportResponse();
    when(taxiService.getReport()).thenReturn(expectedResponse);
    ReportResponse response = taxiController.getReport();
    assertThat(response).isEqualTo(expectedResponse);
  }

  @Test
  void testUpdateState() {
    UpdateTaxiRequest updateTaxiRequest = new UpdateTaxiRequest();
    updateTaxiRequest.setState(State.BOOKED);
    taxiController.update("TAX-01", updateTaxiRequest);
    verify(taxiService, times(1)).update("TAX-01", updateTaxiRequest);
  }

  @Test
  void testBook() {
    BookingRequest bookingRequest = new BookingRequest();
    bookingRequest.setLatitude(2D);
    bookingRequest.setLatitude(1D);
    taxiController.book(bookingRequest);
    verify(taxiService, times(1)).book(bookingRequest);
  }
}
