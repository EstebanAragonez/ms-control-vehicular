package com.apivehicular.presentation.controller;

import com.apivehicular.domain.model.EntryRegistration;
import com.apivehicular.domain.service.EntryRegistrationService;
import com.apivehicular.presentation.dto.request.EntryRegistrationPlateRequest;
import com.apivehicular.presentation.dto.request.EntryRegistrationRequest;
import com.apivehicular.presentation.dto.response.EntryRegistrationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/entry-registrations")
@RequiredArgsConstructor
public class EntryRegistrationController {

    private final EntryRegistrationService entryRegistrationService;

    @GetMapping
    public Flux<EntryRegistrationResponse> getAllEntryRegistrations() {
        return entryRegistrationService.findAll()
                .map(this::mapToResponse);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<EntryRegistrationResponse>> getEntryRegistrationById(@PathVariable Long id) {
        return entryRegistrationService.findById(id)
                .map(entryRegistration -> ResponseEntity.ok(mapToResponse(entryRegistration)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/vehicle/{vehicleId}")
    public Flux<EntryRegistrationResponse> getEntryRegistrationsByVehicleId(@PathVariable Long vehicleId) {
        return entryRegistrationService.findByVehicleId(vehicleId)
                .map(this::mapToResponse);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<EntryRegistrationResponse> createEntryRegistration(@RequestBody EntryRegistrationRequest request) {

        EntryRegistration entryRegistration = mapToModel(request);
        return entryRegistrationService.save(entryRegistration)
                .map(this::mapToResponse);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<EntryRegistrationResponse>> updateEntryRegistration(@PathVariable Long id, @RequestBody EntryRegistrationRequest request) {

        EntryRegistration entryRegistration = mapToModel(request);
        return entryRegistrationService.save(entryRegistration)
                .map(updatedEntry -> ResponseEntity.ok(mapToResponse(updatedEntry)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteEntryRegistration(@PathVariable Long id) {
        return entryRegistrationService.deleteById(id);
    }

    @PostMapping("/by-plate")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<EntryRegistrationResponse> handleVehicleEntryExit(
            @Valid @RequestBody EntryRegistrationPlateRequest request) {
        return entryRegistrationService.processEntryExitByPlate(request.getPlate())
                .map(this::mapToResponse);
    }

    private EntryRegistration mapToModel(EntryRegistrationRequest request) {
        return EntryRegistration.builder()
                .dateTimeEntry(LocalDateTime.parse(request.getDateTimeEntry()))
                .dateTimeDeparture(request.getDateTimeDeparture() != null ? LocalDateTime.parse(request.getDateTimeDeparture()) : null) // Convierte la cadena a LocalDateTime
                .observations(request.getObservations())
                .vehicleId(request.getVehicleId())
                .build();
    }

    private EntryRegistrationResponse mapToResponse(EntryRegistration entryRegistration) {
        return EntryRegistrationResponse.builder()
                .id(entryRegistration.getId())
                .dateTimeEntry(entryRegistration.getDateTimeEntry().toString())
                .dateTimeDeparture(entryRegistration.getDateTimeDeparture() != null ? entryRegistration.getDateTimeDeparture().toString() : null) // Convierte LocalDateTime a cadena
                .observations(entryRegistration.getObservations())
                .vehicleId(entryRegistration.getVehicleId())
                .build();
    }
}
