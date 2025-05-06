package com.apivehicular.domain.service;

import com.apivehicular.domain.exception.VehicleNotFoundException;
import com.apivehicular.domain.model.EntryRegistration;
import com.apivehicular.domain.model.EntryRegistrationDetail;
import com.apivehicular.domain.repository.EntryRegistrationRepository;
import com.apivehicular.domain.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class EntryRegistrationService {

    private final EntryRegistrationRepository entryRegistrationRepository;
    private final VehicleRepository vehicleRepository;
    private final VehicleService vehicleService;
    private final UserService userService;

    public Mono<EntryRegistration> findById(Long id) {
        return entryRegistrationRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Entry Registration not found with id: " + id)));
    }

    public Flux<EntryRegistration> findByVehicleId(Long vehicleId) {
        return entryRegistrationRepository.findByVehicleId(vehicleId);
    }

    public Flux<EntryRegistration> findByDateTimeEntryBetween(LocalDateTime start, LocalDateTime end) {
        return entryRegistrationRepository.findByDateTimeEntryBetween(start, end);
    }

    public Flux<EntryRegistration> findAll() {
        return entryRegistrationRepository.findAll();
    }

    public Mono<EntryRegistration> save(EntryRegistration entryRegistration) {
        return entryRegistrationRepository.save(entryRegistration);
    }

    public Mono<Void> deleteById(Long id) {
        return entryRegistrationRepository.deleteById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Entry Registration not found with id: " + id)));
    }

    public Mono<EntryRegistration> processEntryExitByPlate(String plate) {
        return vehicleRepository.findByPlate(plate)
                .switchIfEmpty(Mono.error(new VehicleNotFoundException("Vehículo no encontrado: " + plate)))
                .flatMap(vehicle -> {
                    LocalDateTime now = LocalDateTime.now();
                    LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();
                    LocalDateTime endOfDay = now.toLocalDate().atTime(23, 59, 59);

                    return entryRegistrationRepository.findLatestByVehicleIdAndDateRange(
                                    vehicle.getId(),
                                    startOfDay,
                                    endOfDay
                            )
                            .flatMap(latestEntry -> {
                                // Caso 2: Existe registro SIN salida -> Actualizar a salida
                                if (latestEntry.getDateTimeDeparture() == null) {
                                    latestEntry.setDateTimeDeparture(now);
                                    latestEntry.setObservations("Salida");
                                    return entryRegistrationRepository.save(latestEntry);
                                }
                                // Caso 3: Existe registro CON salida -> Crear nuevo ingreso
                                return createNewEntry(vehicle.getId(), now);
                            })
                            // Caso 1: No existe registro -> Crear nuevo ingreso
                            .switchIfEmpty(createNewEntry(vehicle.getId(), now));
                });
    }

    private Mono<EntryRegistration> createNewEntry(Long vehicleId, LocalDateTime entryTime) {
        return entryRegistrationRepository.save(
                EntryRegistration.builder()
                        .vehicleId(vehicleId)
                        .dateTimeEntry(entryTime)
                        .observations("Ingreso")
                        .build()
        );
    }

    public Flux<EntryRegistrationDetail> findAllWithDetails() {
        return entryRegistrationRepository.findAll()
                .flatMap(this::enrichWithVehicleAndUserInfo);
    }

    public Flux<EntryRegistrationDetail> findAllTodayWithDetails() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        return entryRegistrationRepository.findByDateTimeEntryBetween(startOfDay, endOfDay)
                .flatMap(this::enrichWithVehicleAndUserInfo);
    }

    private Mono<EntryRegistrationDetail> enrichWithVehicleAndUserInfo(EntryRegistration entryRegistration) {
        return vehicleService.findById(entryRegistration.getVehicleId())
                .flatMap(vehicle -> userService.findById(vehicle.getUser().getId())
                        .map(user -> EntryRegistrationDetail.builder()
                                .entryRegistration(entryRegistration)
                                .vehicle(vehicle)
                                .user(user)
                                .build()
                        )
                );
    }

}
