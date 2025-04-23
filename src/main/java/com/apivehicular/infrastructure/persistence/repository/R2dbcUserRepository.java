package com.apivehicular.infrastructure.persistence.repository;

import com.apivehicular.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface R2dbcUserRepository extends R2dbcRepository<UserEntity, Long> {
    Mono<UserEntity> findByEmail(String email);
    Mono<UserEntity> findByCedula(String cedula);
}
