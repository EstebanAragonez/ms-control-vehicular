package com.apivehicular.infrastructure.persistence.repository;

import com.apivehicular.domain.model.Vehicle;
import com.apivehicular.domain.repository.VehicleRepository;
import com.apivehicular.infrastructure.persistence.mapper.VehicleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class VehicleRepositoryImpl implements VehicleRepository {

    private final R2dbcVehicleRepository r2dbcVehicleRepository;
    private final VehicleMapper vehicleMapper;

    @Override
    public Mono<Vehicle> findById(Long id) {
        return r2dbcVehicleRepository.findById(id)
                .flatMap(vehicleMapper::toModel);
    }

    @Override
    public Mono<Vehicle> findByPlate(String plate) {
        return r2dbcVehicleRepository.findByPlate(plate)
                .flatMap(vehicleMapper::toModel);
    }

    @Override
    public Flux<Vehicle> findByUserId(Long userId) {
        return r2dbcVehicleRepository.findByUserId(userId)
                .flatMap(vehicleMapper::toModel);
    }

    @Override
    public Flux<Vehicle> findAll() {
        return r2dbcVehicleRepository.findAll()
                .flatMap(vehicleMapper::toModel);
    }

    @Override
    public Mono<Vehicle> save(Vehicle vehicle) {
        return vehicleMapper.toEntity(vehicle)
                .flatMap(r2dbcVehicleRepository::save)
                .flatMap(vehicleMapper::toModel);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return r2dbcVehicleRepository.deleteById(id);
    }
}
