package com.apivehicular.infrastructure.persistence.repository;

import com.apivehicular.infrastructure.persistence.entity.EntryRegistrationEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface R2dbcEntryRegistrationRepository extends R2dbcRepository<EntryRegistrationEntity, Long> {

    Flux<EntryRegistrationEntity> findByVehicleId(Long vehicleId);

    @Query("SELECT * FROM entry_registration WHERE date_time_entry BETWEEN :start AND :end")
    Flux<EntryRegistrationEntity> findByDateTimeEntryBetween(LocalDateTime start, LocalDateTime end);

    @Query("""
    SELECT * FROM entry_registration 
    WHERE id_vehicle = :vehicleId 
    AND date_time_entry BETWEEN :start AND :end
    ORDER BY date_time_entry DESC 
    LIMIT 1
""")
    Mono<EntryRegistrationEntity> findLatestByVehicleIdAndDateRange(
            @Param("vehicleId") Long vehicleId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}
