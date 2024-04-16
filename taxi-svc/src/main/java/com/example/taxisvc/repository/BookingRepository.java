package com.example.taxisvc.repository;

import com.example.taxisvc.entity.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingRepository extends MongoRepository<Booking, String> {
}
