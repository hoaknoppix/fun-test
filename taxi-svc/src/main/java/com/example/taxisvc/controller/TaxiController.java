package com.example.taxisvc.controller;

import com.example.taxisvc.dto.BookingRequest;
import com.example.taxisvc.dto.ReportResponse;
import com.example.taxisvc.dto.TaxiResponse;
import com.example.taxisvc.dto.UpdateTaxiRequest;
import com.example.taxisvc.service.TaxiService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TaxiController {

  private final TaxiService taxiService;

  @GetMapping(value = "/taxis", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<TaxiResponse> getTaxis() {
    return taxiService.getTaxis();
  }

  @GetMapping(value = "/report", produces = MediaType.APPLICATION_JSON_VALUE)
  public ReportResponse getReport() {
    return taxiService.getReport();
  }

  @PatchMapping(value = "/taxis/{taxi-id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public void update(@PathVariable("taxi-id") String taxiId, @RequestBody UpdateTaxiRequest updateTaxiRequest) {
    taxiService.update(taxiId, updateTaxiRequest);
  }

  @PostMapping(value = "/booking", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public void book(@RequestBody BookingRequest bookingRequest) {
    taxiService.book(bookingRequest);
  }
}

