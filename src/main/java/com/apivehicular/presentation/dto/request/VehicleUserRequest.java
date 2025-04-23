package com.apivehicular.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleUserRequest {

    @NotBlank(message = "Plate is required")
    private String plate;

    @NotBlank(message = "Type is required")
    private String type;

    @NotBlank(message = "Trademark is required")
    private String trademark;

    @NotBlank(message = "Model is required")
    private String model;

    @NotBlank(message = "SOAT expiration date is required")
    private String soatExpiDate;

    @NotBlank(message = "Technical Mechanical expiration date is required")
    private String techMechaExpiDate;

    @NotBlank(message = "Color is required")
    private String color;

    @NotBlank(message = "User Cedula is required")
    private String cedula;
}
