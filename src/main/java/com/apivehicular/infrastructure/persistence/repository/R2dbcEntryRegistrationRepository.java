package com.apivehicular.infrastructure.persistence.repository;

import com.apivehicular.infrastructure.persistence.entity.EntryRegistrationEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface R2dbcEntryRegistrationRepository extends R2dbcRepository<EntryRegistrationEntity, Long> {

    Flux<EntryRegistrationEntity> findByVehicleId(Long vehicleId);

    @Query("SELECT * FROM entry_registration WHERE date_time_entry BETWEEN :start AND :end")
    Flux<EntryRegistrationEntity> findByDateTimeEntryBetween(LocalDateTime start, LocalDateTime end);
}
