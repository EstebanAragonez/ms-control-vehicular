package com.apivehicular.domain.service;

import com.apivehicular.domain.model.EntryRegistration;
import com.apivehicular.domain.repository.EntryRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EntryRegistrationService {

    private final EntryRegistrationRepository entryRegistrationRepository;

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
}
