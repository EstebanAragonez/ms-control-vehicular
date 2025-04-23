package com.apivehicular.domain.service;

import com.apivehicular.domain.model.Vehicle;
import com.apivehicular.domain.repository.UserRepository;
import com.apivehicular.domain.repository.VehicleRepository;
import com.apivehicular.presentation.dto.request.VehicleUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    public static final String VEHICLE_NOT_FOUND_MESSAGE = "Vehicle not found with id: ";

    public Mono<Vehicle> findById(Long id) {
        return vehicleRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException(VEHICLE_NOT_FOUND_MESSAGE + id)));
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
                .switchIfEmpty(Mono.error(new RuntimeException(VEHICLE_NOT_FOUND_MESSAGE + id)));
    }

    public Mono<Void> deleteVehicle(Long id) {
        return vehicleRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException(VEHICLE_NOT_FOUND_MESSAGE + id)))
                .flatMap(vehicle -> vehicleRepository.deleteById(id));
    }

    public Mono<Vehicle> createVehicleForUser(VehicleUserRequest vehicleUserRequest) {

        return userRepository.findByCedula(vehicleUserRequest.getCedula())
                .flatMap(user ->
                        vehicleRepository.findByPlate(vehicleUserRequest.getPlate())
                                .flatMap(existingVehicle ->
                                        Mono.<Vehicle>error(new RuntimeException("Vehicle with the same plate already exists"))
                                )
                                .switchIfEmpty(
                                        Mono.defer(() ->
                                                vehicleRepository.save(
                                                        Vehicle.builder()
                                                                .plate(vehicleUserRequest.getPlate())
                                                                .type(vehicleUserRequest.getType())
                                                                .trademark(vehicleUserRequest.getTrademark())
                                                                .model(vehicleUserRequest.getModel())
                                                                .soatExpiDate(LocalDate.parse(vehicleUserRequest.getSoatExpiDate()))  // Asignación directa a LocalDate
                                                                .techMechaExpiDate(LocalDate.parse(vehicleUserRequest.getTechMechaExpiDate())) // Asignación directa a LocalDate
                                                                .color(vehicleUserRequest.getColor())
                                                                .user(user)
                                                                .build()
                                                )
                                        )
                                )
                )
                .switchIfEmpty(Mono.error(new RuntimeException("User not found with cedula: " + vehicleUserRequest.getCedula())));
    }

}
