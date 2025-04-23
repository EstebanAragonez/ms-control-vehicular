package com.apivehicular.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWithVehiclesRequest {

    private String name;
    private String lastName;
    private String cedula;
    private String email;
    private String password;
    private Long idRol; // Rol ID
    private List<VehicleRequest> vehicles; // Lista de vehículos asociados al usuario
}
