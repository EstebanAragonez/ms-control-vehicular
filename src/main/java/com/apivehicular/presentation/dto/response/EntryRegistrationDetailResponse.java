package com.apivehicular.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntryRegistrationDetailResponse {

    // Datos del registro de entrada/salida
    private Long id;
    private String dateTimeEntry;
    private String dateTimeDeparture;
    private String observations;

    // Datos del vehículo
    private Long vehicleId;
    private String vehiclePlate;
    private String vehicleModel;
    private String vehicleBrand;
    private String vehicleColor;

    // Datos del usuario
    private Long userId;
    private String userName;
    private String userEmail;
    private String userDocumentNumber;
}