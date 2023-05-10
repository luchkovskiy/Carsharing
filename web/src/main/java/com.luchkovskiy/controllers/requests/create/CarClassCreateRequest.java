package com.luchkovskiy.controllers.requests.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Validated
public class CarClassCreateRequest {

    @Max(20)
    @NotBlank
    private String name;

    @NotEmpty
    private Integer accessLevel;

    @NotBlank
    private String comfortType;

    @NotEmpty
    @Max(10)
    private Float pricePerHour;

}
