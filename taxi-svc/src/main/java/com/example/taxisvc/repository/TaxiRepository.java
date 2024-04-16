package com.example.taxisvc.repository;

import com.example.taxisvc.entity.State;
import com.example.taxisvc.entity.Taxi;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaxiRepository extends MongoRepository<Taxi, String> {
  Integer countTaxisByState(State state);
  List<Taxi> findAllByState(State state);
  Optional<Taxi> findByTaxiId(String taxiId);
}
