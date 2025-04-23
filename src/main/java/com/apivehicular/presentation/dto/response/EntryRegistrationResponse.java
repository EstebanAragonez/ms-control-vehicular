package com.apivehicular.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntryRegistrationResponse {
    private Long id;
    private String dateTimeEntry;
    private String dateTimeDeparture;
    private String observations;
    private Long vehicleId;
}
