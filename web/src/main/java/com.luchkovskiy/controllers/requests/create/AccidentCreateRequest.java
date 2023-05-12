package com.luchkovskiy.controllers.requests.create;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Validated
@Schema(description = "Accident information to save in database")
public class AccidentCreateRequest {

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "1", type = "Long", description = "Id of the session in which the accident happened")
    @Min(1)
    private Long sessionId;

    @NotNull
    @Size(min = 3, max = 50)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Car issue", type = "String", description = "Name of accident")
    private String name;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "50.5", type = "Float", description = "Fine that user have to pay to company")
    @Min(1)
    private Float fine;

    @NotNull
    @PastOrPresent
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-02-22 17:24:01", type = "LocalDateTime",
            description = "The time when the accident happened")
    private LocalDateTime time;

    @NotNull
    @Max(5)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "0.5", type = "Float", description = "User rating subtraction")
    private Float ratingSubtraction;

    @NotNull
    @Min(1)
    @Max(5)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2", type = "Integer", description = "Car damage scale from 1 to 5")
    private Integer damageLevel;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "true", type = "Boolean", description = "Does the car need repair after the incident?")
    private Boolean critical;

}
