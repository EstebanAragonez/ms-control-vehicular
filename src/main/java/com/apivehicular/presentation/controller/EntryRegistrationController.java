package com.apivehicular.presentation.controller;

import com.apivehicular.domain.model.EntryRegistration;
import com.apivehicular.domain.service.EntryRegistrationService;
import com.apivehicular.presentation.dto.request.EntryRegistrationRequest;
import com.apivehicular.presentation.dto.response.EntryRegistrationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/entry-registrations")
@RequiredArgsConstructor
public class EntryRegistrationController {

    private final EntryRegistrationService entryRegistrationService;

    // Obtener todos los registros de entrada
    @GetMapping
    public Flux<EntryRegistrationResponse> getAllEntryRegistrations() {
        return entryRegistrationService.findAll()
                .map(this::mapToResponse); // Mapea a la respuesta DTO
    }

    // Obtener un registro de entrada por ID
    @GetMapping("/{id}")
    public Mono<ResponseEntity<EntryRegistrationResponse>> getEntryRegistrationById(@PathVariable Long id) {
        return entryRegistrationService.findById(id)
                .map(entryRegistration -> ResponseEntity.ok(mapToResponse(entryRegistration))) // Mapea y devuelve la respuesta
                .defaultIfEmpty(ResponseEntity.notFound().build()); // Devuelve 404 si no se encuentra
    }

    // Obtener registros de entrada por ID de vehículo
    @GetMapping("/vehicle/{vehicleId}")
    public Flux<EntryRegistrationResponse> getEntryRegistrationsByVehicleId(@PathVariable Long vehicleId) {
        return entryRegistrationService.findByVehicleId(vehicleId)
                .map(this::mapToResponse); // Mapea y devuelve la respuesta DTO
    }

    // Crear un nuevo registro de entrada
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<EntryRegistrationResponse> createEntryRegistration(@RequestBody EntryRegistrationRequest request) {
        // Mapea la solicitud a un modelo de dominio y lo guarda
        EntryRegistration entryRegistration = mapToModel(request);
        return entryRegistrationService.save(entryRegistration)
                .map(this::mapToResponse); // Mapea y devuelve la respuesta DTO
    }

    // Actualizar un registro de entrada
    @PutMapping("/{id}")
    public Mono<ResponseEntity<EntryRegistrationResponse>> updateEntryRegistration(@PathVariable Long id, @RequestBody EntryRegistrationRequest request) {
        // Mapea la solicitud a un modelo de dominio y lo actualiza
        EntryRegistration entryRegistration = mapToModel(request);
        return entryRegistrationService.save(entryRegistration)
                .map(updatedEntry -> ResponseEntity.ok(mapToResponse(updatedEntry))) // Mapea y devuelve la respuesta DTO
                .defaultIfEmpty(ResponseEntity.notFound().build()); // Devuelve 404 si no se encuentra
    }

    // Eliminar un registro de entrada
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteEntryRegistration(@PathVariable Long id) {
        return entryRegistrationService.deleteById(id); // Llama al servicio para eliminar el registro
    }

    private EntryRegistration mapToModel(EntryRegistrationRequest request) {
        return EntryRegistration.builder()
                .dateTimeEntry(LocalDateTime.parse(request.getDateTimeEntry())) // Convierte la cadena a LocalDateTime
                .dateTimeDeparture(request.getDateTimeDeparture() != null ? LocalDateTime.parse(request.getDateTimeDeparture()) : null) // Convierte la cadena a LocalDateTime
                .observations(request.getObservations())
                .vehicleId(request.getVehicleId()) // El vehicleId se mapea directamente
                .build();
    }

    private EntryRegistrationResponse mapToResponse(EntryRegistration entryRegistration) {
        return EntryRegistrationResponse.builder()
                .id(entryRegistration.getId())
                .dateTimeEntry(entryRegistration.getDateTimeEntry().toString()) // Convierte LocalDateTime a cadena
                .dateTimeDeparture(entryRegistration.getDateTimeDeparture() != null ? entryRegistration.getDateTimeDeparture().toString() : null) // Convierte LocalDateTime a cadena
                .observations(entryRegistration.getObservations())
                .vehicleId(entryRegistration.getVehicleId()) // El ID del vehículo
                .build();
    }
}
