package com.luchkovskiy.controllers.requests.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Validated
public class CarClassCreateRequest {

    private String name;

    private Integer accessLevel;

    private String comfortType;

    private Float pricePerHour;

}
