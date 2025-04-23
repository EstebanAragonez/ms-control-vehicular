package com.apivehicular.domain.service;

import com.apivehicular.domain.model.Rol;
import com.apivehicular.domain.model.User;
import com.apivehicular.domain.model.Vehicle;
import com.apivehicular.domain.repository.RolRepository;
import com.apivehicular.domain.repository.UserRepository;
import com.apivehicular.domain.repository.VehicleRepository;
import com.apivehicular.presentation.dto.request.UserWithVehiclesRequest;
import com.apivehicular.presentation.dto.request.VehicleRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public Mono<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    public Mono<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Mono<User> updateUser(Long id, User user) {
        return userRepository.findById(id)
                .flatMap(existingUser -> {
                    existingUser.setName(user.getName());
                    existingUser.setLastName(user.getLastName());
                    existingUser.setEmail(user.getEmail());
                    existingUser.setRol(user.getRol());

                    if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
                    }

                    return userRepository.save(existingUser);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")));
    }

    public Mono<Void> deleteUser(Long id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")))
                .flatMap(user -> userRepository.deleteById(id));
    }


    public Mono<User> createUserWithVehicles(UserWithVehiclesRequest request) {

        Mono<Rol> roleMono = rolRepository.findById(request.getIdRol());

        return roleMono.flatMap(role -> {

            User user = User.builder()
                    .name(request.getName())
                    .lastName(request.getLastName())
                    .cedula(request.getCedula())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword())) // Encriptamos la contraseña
                    .rol(role)
                    .build();

            return userRepository.save(user).flatMap(savedUser -> {

                return Mono.just(savedUser)
                        .flatMap(user1 -> {
                            return Mono.when(
                                    request.getVehicles().stream()
                                            .map(vehicleRequest -> saveVehicle(vehicleRequest, user1))
                                            .toArray(Mono[]::new) // Transforma la lista de vehículos en un array de Monos
                            ).thenReturn(user1); // Devolvemos el usuario
                        });
            });
        });
    }

    private Mono<Vehicle> saveVehicle(VehicleRequest vehicleRequest, User user) {
        Vehicle vehicle = Vehicle.builder()
                .plate(vehicleRequest.getPlate())
                .type(vehicleRequest.getType())
                .trademark(vehicleRequest.getTrademark())
                .model(vehicleRequest.getModel())
                .soatExpiDate(LocalDateTime.parse(vehicleRequest.getSoatExpiDate()))  // Conversión a LocalDateTime
                .techMechaExpiDate(LocalDateTime.parse(vehicleRequest.getTechMechaExpiDate())) // Conversión a LocalDateTime
                .color(vehicleRequest.getColor())
                .user(user) // Asocia el usuario al vehículo
                .build();

        return vehicleRepository.save(vehicle); // Guarda el vehículo en la base de datos
    }
}