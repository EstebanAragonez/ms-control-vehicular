package com.apivehicular.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private LocalDateTime soatExpiDate;
    private LocalDateTime techMechaExpiDate;
    private String color;
    private User user;

}
