package com.apivehicular.domain.repository;

import com.apivehicular.domain.model.Vehicle;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VehicleRepository {
    Mono<Vehicle> findById(Long id);
    Mono<Vehicle> findByPlate(String plate);
    Flux<Vehicle> findByUserId(Long userId);
    Flux<Vehicle> findAll();
    Mono<Vehicle> save(Vehicle vehicle);
    Mono<Void> deleteById(Long id);
}
