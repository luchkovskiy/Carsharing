package com.luchkovskiy.controllers.requests.create;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Validated
@Schema(description = "Car class information to save in database")
public class CarClassCreateRequest {

    @Size(min = 4, max = 25)
    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Business", type = "string", description = "Name of car class")
    private String name;

    @NotNull
    @Min(1)
    @Max(3)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer", description = "Access level to be used by user subscription from 1 to 3")
    private Integer accessLevel;

    @NotNull
    @Size(min = 3, max = 50)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "High", type = "string", description = "Comfort level of current class: may be high, low or normal")
    private String comfortType;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "16.4", type = "number", description = "Current price for the car rent")
    @Min(10)
    private Float pricePerHour;

}
