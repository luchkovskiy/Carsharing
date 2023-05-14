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
@Schema(description = "Subscription level information to save in database")
public class SubscriptionLevelCreateRequest {

    @NotNull
    @Min(1)
    @Max(3)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2", type = "integer", description = "Subscription's access level")
    private Integer accessLevel;

    @NotNull
    @Min(80)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "97.5", type = "number", description = "Subscription's price per day")
    private Float pricePerDay;

    @NotNull
    @Size(min = 3, max = 30)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Standard", type = "string", description = "Subscription's level name")
    private String name;

}
