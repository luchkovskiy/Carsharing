package com.luchkovskiy.controllers.requests.create;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

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
@Schema(description = "Session information to save in database")
public class SessionCreateRequest {

    @NotNull
    @Min(1)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "Long", description = "User's Id in database")
    private Long userId;

    @NotNull
    @Min(1)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "Long", description = "Car's Id in database")
    private Long carId;

    @NotNull
    @PastOrPresent
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-02-22 17:24:01", type = "LocalDateTime",
            description = "The time when the session started")
    private LocalDateTime startTime;

    @PastOrPresent
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$")
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "2023-02-22 19:14:56", type = "LocalDateTime",
            description = "The time when the session ended")
    private LocalDateTime endTime;

    @NotNull
    @Size(min = 3, max = 20)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Active", type = "String", description = "Status of the session")
    private String status;

    @Min(0)
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "15.5", type = "Float", description = "Amount of kilometers passed during the session")
    private Float distancePassed;

}
