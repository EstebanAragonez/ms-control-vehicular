package com.apivehicular.domain.service;

import com.apivehicular.domain.model.Rol;
import com.apivehicular.domain.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class RolService {

    private final RolRepository rolRepository;

    public Mono<Rol> findById(Long id) {
        return rolRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Role not found with id: " + id)));
    }

    public Flux<Rol> findAll() {
        return rolRepository.findAll();
    }


    public Mono<Rol> updateRole(Long id, Rol rol) {
        return rolRepository.findById(id)
                .flatMap(existingRole -> {
                    existingRole.setNameRol(rol.getNameRol());
                    return rolRepository.save(existingRole);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Role not found with id: " + id)));
    }

    public Mono<Void> deleteRole(Long id) {
        return rolRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Role not found with id: " + id)))
                .flatMap(role -> rolRepository.deleteById(id));
    }
}
