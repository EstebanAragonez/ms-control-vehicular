package com.service.ms.control_vehicular.domain.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private Rol rol;
    private Vehiculo vehiculo;
    private RegistroIngreso registroIngreso;
}
