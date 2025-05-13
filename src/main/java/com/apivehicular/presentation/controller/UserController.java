package com.apivehicular.presentation.controller;

import com.apivehicular.domain.model.Rol;
import com.apivehicular.domain.model.User;
import com.apivehicular.domain.service.UserService;
import com.apivehicular.presentation.dto.request.UserRequest;
import com.apivehicular.presentation.dto.request.UserWithVehiclesRequest;
import com.apivehicular.presentation.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public Flux<UserResponse> getAllUsers() {
        return userService.findAll()
                .map(this::mapToResponse);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserResponse>> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> ResponseEntity.ok(mapToResponse(user)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserResponse> createUserWithVehicles(@RequestBody UserWithVehiclesRequest request) {
        return userService.createUserWithVehicles(request)
                .map(user -> mapToResponse(user)); // Mapea la respuesta a UserResponse
    }


    private User mapToModel(UserRequest request, Rol rol) {
        return User.builder()
                .name(request.getName())
                .lastName(request.getLastName())
                .cedula(request.getCedula())
                .email(request.getEmail())
                .password(request.getPassword())
                .rol(rol)
                .build();
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .lastName(user.getLastName())
                .cedula(user.getCedula())
                .email(user.getEmail())
                .roleName(user.getRol().getNameRol())
                .build();
    }


}
