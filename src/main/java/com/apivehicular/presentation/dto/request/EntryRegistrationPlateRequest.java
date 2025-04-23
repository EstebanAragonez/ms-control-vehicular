package com.apivehicular.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EntryRegistrationPlateRequest {

    @NotBlank
    private String plate;
}
