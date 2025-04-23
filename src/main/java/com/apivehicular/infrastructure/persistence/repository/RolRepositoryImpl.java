package com.apivehicular.infrastructure.persistence.repository;

import com.apivehicular.domain.model.Rol;
import com.apivehicular.domain.repository.RolRepository;
import com.apivehicular.infrastructure.persistence.entity.RolEntity;
import com.apivehicular.infrastructure.persistence.mapper.RolMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class RolRepositoryImpl implements RolRepository {
    private final R2dbcRolRepository r2dbcRoleRepository;
    private final RolMapper roleMapper;

    @Override
    public Mono<Rol> findById(Long id) {
        return r2dbcRoleRepository.findById(id)
                .map(roleMapper::toModel);
    }

    @Override
    public Mono<Rol> findByName(String name) {
        return r2dbcRoleRepository.findByName(name)
                .map(roleMapper::toModel);
    }

    @Override
    public Flux<Rol> findAll() {
        return r2dbcRoleRepository.findAll()
                .map(roleMapper::toModel);
    }

    @Override
    public Mono<Rol> save(Rol rol) {
        RolEntity entity = roleMapper.toEntity(rol);
        return r2dbcRoleRepository.save(entity)
                .map(roleMapper::toModel);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return r2dbcRoleRepository.deleteById(id);
    }
}
