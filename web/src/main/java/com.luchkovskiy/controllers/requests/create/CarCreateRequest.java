package com.luchkovskiy.controllers.requests.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Validated
public class CarCreateRequest {

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @NotEmpty
    private Float maxSpeed;

    @NotBlank
    private String color;

    @NotEmpty
    private Integer releaseYear;

    @NotBlank
    private String gearboxType;

    @NotEmpty
    private Integer sitsAmount;

    @NotEmpty
    private Long classId;

    @NotEmpty
    private Float gasConsumption;

    @NotBlank
    private String licensePlateNumber;

}
