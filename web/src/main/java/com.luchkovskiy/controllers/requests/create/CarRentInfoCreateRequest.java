package com.luchkovskiy.controllers.requests.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Validated
public class CarRentInfoCreateRequest {

    @NotEmpty
    private Long carId;

    @NotEmpty
    private Float gasRemaining;

    @NotEmpty
    private Boolean repairing;

    @NotBlank
    private String currentLocation;

    @NotEmpty
    private Boolean available;

    @NotEmpty
    private Float condition;
}
