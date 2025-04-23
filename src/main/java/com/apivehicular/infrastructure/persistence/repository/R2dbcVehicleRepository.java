package com.apivehicular.infrastructure.persistence.repository;

import com.apivehicular.infrastructure.persistence.entity.VehicleEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface R2dbcVehicleRepository extends R2dbcRepository<VehicleEntity, Long> {
    Mono<VehicleEntity> findByPlate(String plate);
    Flux<VehicleEntity> findByUserId(Long userId);
}
