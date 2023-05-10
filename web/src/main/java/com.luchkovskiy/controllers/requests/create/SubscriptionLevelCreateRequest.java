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
public class SubscriptionLevelCreateRequest {

    @NotEmpty
    private Integer accessLevel;

    @NotEmpty
    private Float pricePerDay;

    @NotBlank
    private String name;

}
