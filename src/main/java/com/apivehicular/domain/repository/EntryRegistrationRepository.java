package com.apivehicular.domain.repository;

import com.apivehicular.domain.model.EntryRegistration;
import org.springframework.data.r2dbc.repository.Query;
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

    @Query("""
        SELECT * FROM entry_registration 
        WHERE id_vehicle = :vehicleId 
        AND date_time_entry BETWEEN :start AND :end
        ORDER BY date_time_entry DESC 
        LIMIT 1
    """)
    Mono<EntryRegistration> findLatestByVehicleIdAndDateRange(
            Long vehicleId,
            LocalDateTime start,
            LocalDateTime end
    );
}
