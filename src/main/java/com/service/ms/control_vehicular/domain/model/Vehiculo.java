package com.service.ms.control_vehicular.domain.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehiculo {
    private Long idVehiculo;
    private String placa;
    private String tipo;
    private String marca;
    private String modelo;
    private String color;
    private Usuario usuario;
}
