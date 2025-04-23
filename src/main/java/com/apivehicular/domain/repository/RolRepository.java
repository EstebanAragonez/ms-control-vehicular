package com.apivehicular.domain.repository;


import com.apivehicular.domain.model.Rol;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RolRepository {
    Mono<Rol> findById(Long id);
    Mono<Rol> findByName(String name);
    Flux<Rol> findAll();
    Mono<Rol> save(Rol role);
    Mono<Void> deleteById(Long id);
}
