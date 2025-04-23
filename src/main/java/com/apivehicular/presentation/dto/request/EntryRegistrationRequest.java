package com.apivehicular.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntryRegistrationRequest {
    private String dateTimeEntry;
    private String dateTimeDeparture;
    private String observations;
    private Long vehicleId;
}
