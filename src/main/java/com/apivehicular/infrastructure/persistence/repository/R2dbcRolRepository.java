package com.apivehicular.infrastructure.persistence.repository;

import com.apivehicular.infrastructure.persistence.entity.RolEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface R2dbcRolRepository extends R2dbcRepository<RolEntity, Long> {
    Mono<RolEntity> findByName(String name);
}
