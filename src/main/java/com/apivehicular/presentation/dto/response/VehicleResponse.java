package com.apivehicular.presentation.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleResponse {
    private Long id;
    private String plate;
    private String type;
    private String trademark;
    private String model;
    private String soatExpiDate;
    private String techMechaExpiDate;
    private String color;
    private Long userId;
    private String userFullName;

}
