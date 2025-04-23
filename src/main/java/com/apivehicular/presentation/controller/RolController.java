package com.apivehicular.presentation.controller;

import com.apivehicular.domain.model.Rol;
import com.apivehicular.domain.service.RolService;
import com.apivehicular.presentation.dto.request.RolRequest;
import com.apivehicular.presentation.dto.response.RolResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolController {
    private final RolService rolService;

    @GetMapping
    public Flux<RolResponse> getAllRoles() {
        return rolService.findAll()
                .map(this::mapToResponse);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<RolResponse>> getRoleById(@PathVariable Long id) {
        return rolService.findById(id)
                .map(rol -> ResponseEntity.ok(mapToResponse(rol)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<RolResponse>> updateRole(@PathVariable Long id, @Valid @RequestBody RolRequest rolRequest) {
        return rolService.updateRole(id, mapToModel(rolRequest))
                .map(updatedRole -> ResponseEntity.ok(mapToResponse(updatedRole)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteRole(@PathVariable Long id) {
        return rolService.deleteRole(id);
    }

    private Rol mapToModel(RolRequest request) {
        return Rol.builder()
                .nameRol(request.getName())
                .build();
    }

    private RolResponse mapToResponse(Rol role) {
        return RolResponse.builder()
                .id(role.getIdRol())
                .name(role.getNameRol())
                .build();
    }
}
