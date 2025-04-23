package com.apivehicular.domain.service;

import com.apivehicular.domain.model.Vehicle;
import com.apivehicular.domain.repository.UserRepository;
import com.apivehicular.domain.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    public Mono<Vehicle> findById(Long id) {
        return vehicleRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Vehicle not found with id: " + id)));
    }

    public Flux<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    public Flux<Vehicle> findByUserId(Long userId) {
        return vehicleRepository.findByUserId(userId);
    }


    public Mono<Vehicle> updateVehicle(Long id, Vehicle vehicle) {
        return vehicleRepository.findById(id)
                .flatMap(existingVehicle -> {
                    existingVehicle.setPlate(vehicle.getPlate());
                    existingVehicle.setType(vehicle.getType());
                    existingVehicle.setTrademark(vehicle.getTrademark());
                    existingVehicle.setModel(vehicle.getModel());
                    existingVehicle.setSoatExpiDate(vehicle.getSoatExpiDate());
                    existingVehicle.setTechMechaExpiDate(vehicle.getTechMechaExpiDate());
                    existingVehicle.setColor(vehicle.getColor());

                    if (vehicle.getUser() != null && vehicle.getUser().getId() != null) {
                        return userRepository.findById(vehicle.getUser().getId())
                                .switchIfEmpty(Mono.error(new RuntimeException("User not found with id: " + vehicle.getUser().getId())))
                                .flatMap(user -> {
                                    existingVehicle.setUser(user);
                                    return vehicleRepository.save(existingVehicle);
                                });
                    } else {
                        return vehicleRepository.save(existingVehicle);
                    }
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Vehicle not found with id: " + id)));
    }

    public Mono<Void> deleteVehicle(Long id) {
        return vehicleRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Vehicle not found with id: " + id)))
                .flatMap(vehicle -> vehicleRepository.deleteById(id));
    }
}
