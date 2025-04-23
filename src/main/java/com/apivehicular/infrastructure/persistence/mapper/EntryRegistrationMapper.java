package com.apivehicular.infrastructure.persistence.mapper;

import com.apivehicular.domain.model.EntryRegistration;
import com.apivehicular.domain.service.VehicleService;
import com.apivehicular.infrastructure.persistence.entity.EntryRegistrationEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class EntryRegistrationMapper {

    private final VehicleService vehicleService;

    public EntryRegistrationMapper(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    public Mono<EntryRegistration> toModel(EntryRegistrationEntity entity) {

        return vehicleService.findById(entity.getVehicleId())
                .map(vehicle -> EntryRegistration.builder()
                        .id(entity.getId())
                        .vehicleId(vehicle.getId())
                        .dateTimeEntry(entity.getDateTimeEntry())
                        .dateTimeDeparture(entity.getDateTimeDeparture())
                        .observations(entity.getObservations())
                        .build());
    }

    public EntryRegistrationEntity toEntity(EntryRegistration model) {
        return EntryRegistrationEntity.builder()
                .id(model.getId())
                .vehicleId(model.getVehicleId())
                .dateTimeEntry(model.getDateTimeEntry())
                .dateTimeDeparture(model.getDateTimeDeparture())
                .observations(model.getObservations())
                .build();
    }
}
