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
@Schema(description = "Car rent information to save in database")
public class CarRentInfoCreateRequest {

    @NotNull
    @Min(1)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "Long", description = "Id of the car")
    private Long carId;

    @NotNull
    @Min(0)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "12.8", type = "Float", description = "Current balance of fuel")
    private Float gasRemaining;

    @NotNull
    @Size(min = 4, max = 5)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "false", type = "Boolean", description = "Is the car repairing right now?")
    private Boolean repairing;

    @Size(min = 8, max = 50)
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "53.978527, 30.395270", type = "Boolean", description = "Latitude and longitude of current car's location")
    private String currentLocation;

    @NotNull
    @Size(min = 4, max = 5)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "false", type = "Boolean", description = "Is the car available right now?")
    private Boolean available;

    @NotNull
    @Min(1)
    @Max(5)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "4.6", type = "Float", description = "Car's condition level from 1 to 5")
    private Float condition;
}
