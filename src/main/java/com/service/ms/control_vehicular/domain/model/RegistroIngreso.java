package com.service.ms.control_vehicular.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroIngreso {
    private Long idRegistro;
    private Vehiculo vehiculo;
    private LocalDateTime fechaHoraEntrada;
    private LocalDateTime fechaHoraSalida;
    private String observaciones;
}
