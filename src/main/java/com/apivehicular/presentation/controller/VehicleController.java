package com.apivehicular.presentation.controller;

import com.apivehicular.domain.model.User;
import com.apivehicular.domain.model.Vehicle;
import com.apivehicular.domain.service.UserService;
import com.apivehicular.domain.service.VehicleService;
import com.apivehicular.presentation.dto.request.VehicleRequest;
import com.apivehicular.presentation.dto.request.VehicleUserRequest;
import com.apivehicular.presentation.dto.response.VehicleResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;
    private final UserService userService;

    @GetMapping
    public Flux<VehicleResponse> getAllVehicles() {
        return vehicleService.findAll()
                .map(this::mapToResponse);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<VehicleResponse>> getVehicleById(@PathVariable Long id) {
        return vehicleService.findById(id)
                .map(vehicle -> ResponseEntity.ok(mapToResponse(vehicle)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public Flux<VehicleResponse> getVehiclesByUserId(@PathVariable Long userId) {
        return vehicleService.findByUserId(userId)
                .map(this::mapToResponse);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<VehicleResponse>> updateVehicle(@PathVariable Long id, @Valid @RequestBody VehicleRequest vehicleRequest) {
        return userService.findById(vehicleRequest.getUserId())
                .flatMap(user -> {
                    Vehicle vehicle = mapToModel(vehicleRequest, user);
                    return vehicleService.updateVehicle(id, vehicle)
                            .map(updatedVehicle -> ResponseEntity.ok(mapToResponse(updatedVehicle)));
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteVehicle(@PathVariable Long id) {
        return vehicleService.deleteVehicle(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<VehicleResponse>> createVehicle(@Valid @RequestBody VehicleUserRequest vehicleUserRequest) {
        return vehicleService.createVehicleForUser(vehicleUserRequest)
                .map(this::mapToResponse)
                .map(ResponseEntity::ok);
    }


    private Vehicle mapToModel(VehicleRequest request, User user) {
        return Vehicle.builder()
                .plate(request.getPlate())
                .type(request.getType())
                .trademark(request.getTrademark())
                .model(request.getModel())
                .soatExpiDate(LocalDate.parse(request.getSoatExpiDate().split("T")[0]))
                .techMechaExpiDate(LocalDate.parse(request.getTechMechaExpiDate().split("T")[0]))
                .color(request.getColor())
                .user(user)
                .build();
    }

    private VehicleResponse mapToResponse(Vehicle vehicle) {
        return VehicleResponse.builder()
                .id(vehicle.getId())
                .plate(vehicle.getPlate())
                .type(vehicle.getType())
                .trademark(vehicle.getTrademark())
                .model(vehicle.getModel())
                .soatExpiDate(vehicle.getSoatExpiDate().toString())
                .techMechaExpiDate(vehicle.getTechMechaExpiDate().toString())
                .color(vehicle.getColor())
                .userId(vehicle.getUser().getId())
                .userFullName(vehicle.getUser().getName() + " " + vehicle.getUser().getLastName())
                .build();
    }

}
