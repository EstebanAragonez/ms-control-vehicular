package com.apivehicular.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntryRegistration {

    private Long id;
    private Long vehicleId;
    private LocalDateTime dateTimeEntry;
    private LocalDateTime dateTimeDeparture;
    private String observations;
}
