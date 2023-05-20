package com.luchkovskiy.controllers.requests.update;

import com.luchkovskiy.controllers.requests.create.CarCreateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Validated
@Schema(description = "Car information to update in database")
public class CarUpdateRequest extends CarCreateRequest {

    @NotNull
    @Min(1)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer", description = "Id of the car")
    private Long id;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "false", type = "boolean", description = "Is car visible in the system?")
    private Boolean visible;

}
