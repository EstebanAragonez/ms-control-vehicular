package com.apivehicular.infrastructure.persistence.repository;

import com.apivehicular.domain.model.EntryRegistration;
import com.apivehicular.domain.repository.EntryRegistrationRepository;
import com.apivehicular.infrastructure.persistence.entity.EntryRegistrationEntity;
import com.apivehicular.infrastructure.persistence.mapper.EntryRegistrationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class EntryRegistrationRepositoryImpl implements EntryRegistrationRepository {

    private final R2dbcEntryRegistrationRepository r2dbcEntryRegistrationRepository;
    private final EntryRegistrationMapper entryRegistrationMapper;

    @Override
    public Mono<EntryRegistration> findById(Long id) {
        return r2dbcEntryRegistrationRepository.findById(id)
                .flatMap(entryRegistrationMapper::toModel);
    }

    @Override
    public Flux<EntryRegistration> findByVehicleId(Long vehicleId) {
        return r2dbcEntryRegistrationRepository.findByVehicleId(vehicleId)
                .flatMap(entryRegistrationMapper::toModel);
    }

    @Override
    public Flux<EntryRegistration> findByDateTimeEntryBetween(LocalDateTime start, LocalDateTime end) {
        return r2dbcEntryRegistrationRepository.findByDateTimeEntryBetween(start, end)
                .flatMap(entryRegistrationMapper::toModel);
    }

    @Override
    public Flux<EntryRegistration> findAll() {
        return r2dbcEntryRegistrationRepository.findAll()
                .flatMap(entryRegistrationMapper::toModel);
    }

    @Override
    public Mono<EntryRegistration> save(EntryRegistration entryRegistration) {
        EntryRegistrationEntity entity = entryRegistrationMapper.toEntity(entryRegistration);
        return r2dbcEntryRegistrationRepository.save(entity)
                .flatMap(entryRegistrationMapper::toModel);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return r2dbcEntryRegistrationRepository.deleteById(id);
    }
}
