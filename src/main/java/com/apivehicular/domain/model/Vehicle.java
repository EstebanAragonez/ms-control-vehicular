package com.apivehicular.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    private Long id;
    private String plate;
    private String type;
    private String trademark;
    private String model;
    private LocalDate soatExpiDate;
    private LocalDate techMechaExpiDate;
    private String color;
    private User user;

}
