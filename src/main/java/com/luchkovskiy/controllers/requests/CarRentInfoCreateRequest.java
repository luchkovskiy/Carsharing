package com.luchkovskiy.controllers.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarRentInfoCreateRequest {
    private Long id;
    private Long carId;
    private Float gasRemaining;
    private Boolean repairing;
    private String currentLocation;
    private Boolean available;
    private Float condition;
}
