package com.example.locationsvc.service;

public interface LocationService {
    void handleLocation(String taxiId, Double latitude, Double longitude);
}
