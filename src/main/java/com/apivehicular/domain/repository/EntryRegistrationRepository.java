package com.apivehicular.domain.repository;

import com.apivehicular.domain.model.EntryRegistration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface EntryRegistrationRepository {
    Mono<EntryRegistration> findById(Long id);
    Flux<EntryRegistration> findByVehicleId(Long vehicleId);
    Flux<EntryRegistration> findByDateTimeEntryBetween(LocalDateTime start, LocalDateTime end);
    Flux<EntryRegistration> findAll();
    Mono<EntryRegistration> save(EntryRegistration entryRegistration);
    Mono<Void> deleteById(Long id);
    Mono<EntryRegistration> findLatestByVehicleIdAndDateRange(Long vehicleId, LocalDateTime start, LocalDateTime end);
}