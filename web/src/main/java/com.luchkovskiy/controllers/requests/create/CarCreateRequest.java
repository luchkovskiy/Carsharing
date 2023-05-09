package com.luchkovskiy.controllers.requests.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarCreateRequest {

    private String brand;
    private String model;
    private Float maxSpeed;
    private String color;
    private Integer releaseYear;
    private String gearboxType;
    private Integer sitsAmount;
    private Long classId;
    private Float gasConsumption;
    private String licensePlateNumber;

}
