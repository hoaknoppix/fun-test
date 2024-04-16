package com.example.locationsvc.repository;

import com.example.locationsvc.entity.Taxi;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaxiRepository extends MongoRepository<Taxi, String> {

}
