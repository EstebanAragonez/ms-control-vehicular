package com.apivehicular.infrastructure.persistence.mapper;

import com.apivehicular.domain.model.User;
import com.apivehicular.domain.repository.RolRepository;
import com.apivehicular.infrastructure.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final RolRepository rolRepository;

    public Mono<User> toModel(UserEntity entity) {
        return rolRepository.findById(entity.getRoleId())
                .map(rol -> {
                    User user = new User();
                    user.setId(entity.getId());
                    user.setName(entity.getName());
                    user.setLastName(entity.getLastName());
                    user.setCedula(entity.getCedula());
                    user.setEmail(entity.getEmail());
                    user.setPassword(entity.getPassword());
                    user.setRol(rol);
                    return user;
                });
    }

    public Mono<UserEntity> toEntity(User model) {
        return Mono.just(UserEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .lastName(model.getLastName())
                .cedula(model.getCedula())
                .email(model.getEmail())
                .password(model.getPassword())
                .roleId(model.getRol().getIdRol())
                .build());
    }

}
