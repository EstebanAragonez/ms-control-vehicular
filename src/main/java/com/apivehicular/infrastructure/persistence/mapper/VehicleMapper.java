package com.apivehicular.infrastructure.persistence.mapper;


import com.apivehicular.domain.model.Vehicle;
import com.apivehicular.domain.repository.UserRepository;
import com.apivehicular.infrastructure.persistence.entity.VehicleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class VehicleMapper {

    private final UserRepository userRepository;

    public Mono<Vehicle> toModel(VehicleEntity entity) {
        return userRepository.findById(entity.getUserId())
                .map(user -> {
                    Vehicle vehicle = new Vehicle();
                    vehicle.setId(entity.getId());
                    vehicle.setPlate(entity.getPlate());
                    vehicle.setType(entity.getType());
                    vehicle.setTrademark(entity.getTrademark());
                    vehicle.setModel(entity.getModel());
                    vehicle.setSoatExpiDate(entity.getSoatExpiDate());
                    vehicle.setTechMechaExpiDate(entity.getTechMechaExpiDate());
                    vehicle.setColor(entity.getColor());
                    vehicle.setUser(user);
                    return vehicle;
                });
    }

    public Mono<VehicleEntity> toEntity(Vehicle model) {
        return Mono.just(VehicleEntity.builder()
                .id(model.getId())
                .plate(model.getPlate())
                .type(model.getType())
                .trademark(model.getTrademark())
                .model(model.getModel())
                .soatExpiDate(model.getSoatExpiDate())
                .techMechaExpiDate(model.getTechMechaExpiDate())
                .color(model.getColor())
                .userId(model.getUser().getId())
                .build());
    }
}
