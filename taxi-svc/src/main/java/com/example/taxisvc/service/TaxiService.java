package com.example.taxisvc.service;

import com.example.taxisvc.dto.BookingRequest;
import com.example.taxisvc.dto.ReportResponse;
import com.example.taxisvc.dto.TaxiResponse;
import com.example.taxisvc.dto.UpdateTaxiRequest;
import java.util.List;

public interface TaxiService {
  List<TaxiResponse> getTaxis();

  ReportResponse getReport();

  void update(String taxiId, UpdateTaxiRequest updateTaxiRequest);

  void book(BookingRequest bookingRequest);
}
