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
        // Obtenemos el vehículo por su ID
        return vehicleService.findById(entity.getVehicleId())
                .map(vehicle -> EntryRegistration.builder()
                        .id(entity.getId())
                        .vehicleId(vehicle.getId())  // Asignamos el objeto completo Vehicle
                        .dateTimeEntry(entity.getDateTimeEntry())
                        .dateTimeDeparture(entity.getDateTimeDeparture())
                        .observations(entity.getObservations())
                        .build());
    }


    // Mapea el modelo de dominio a la entidad de la base de datos
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
