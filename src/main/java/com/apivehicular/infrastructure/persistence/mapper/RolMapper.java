package com.apivehicular.infrastructure.persistence.mapper;

import com.apivehicular.domain.model.Rol;
import com.apivehicular.infrastructure.persistence.entity.RolEntity;
import org.springframework.stereotype.Component;

@Component
public class RolMapper {
    public Rol toModel(RolEntity entity) {
        return Rol.builder()
                .idRol(entity.getId())
                .nameRol(entity.getName())
                .build();
    }

    public RolEntity toEntity(Rol model) {
        return RolEntity.builder()
                .id(model.getIdRol())
                .name(model.getNameRol())
                .build();
    }
}
