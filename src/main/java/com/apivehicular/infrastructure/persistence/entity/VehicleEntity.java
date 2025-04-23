package com.apivehicular.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("vehicle")
public class VehicleEntity {

    @Id
    @Column("id_vehicle")
    private Long id;

    @Column("plate")
    private String plate;

    @Column("type")
    private String type;

    @Column("trademark")
    private String trademark;

    @Column("model")
    private String model;

    @Column("soat_expi_date")
    private LocalDateTime soatExpiDate;

    @Column("tech_mecha_expi_date")
    private LocalDateTime techMechaExpiDate;

    @Column("color")
    private String color;

    @Column("id_user")
    private Long userId;

}
