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
@Table("entry_registration")
public class EntryRegistrationEntity {

    @Id
    @Column("id_registration")
    private Long id;

    @Column("id_vehicle")
    private Long vehicleId;

    @Column("date_time_entry")
    private LocalDateTime dateTimeEntry;

    @Column("date_time_departure")
    private LocalDateTime dateTimeDeparture;

    @Column("observations")
    private String observations;
}
