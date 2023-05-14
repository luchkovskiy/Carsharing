package com.luchkovskiy.controllers.requests.create;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
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
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer", description = "User's Id in database")
    private Long userId;

    @NotNull
    @Min(1)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer", description = "Car's Id in database")
    private Long carId;

    @NotNull
    @PastOrPresent
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-02-22T17:24:01", type = "date-time",
            description = "The time when the session started")
    private LocalDateTime startTime;

    @PastOrPresent
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "2023-02-22T19:14:56", type = "date-time",
            description = "The time when the session ended")
    private LocalDateTime endTime;

    @NotNull
    @Size(min = 3, max = 20)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Active", type = "string", description = "Status of the session")
    private String status;

    @Min(0)
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "15.5", type = "number", description = "Amount of kilometers passed during the session")
    private Float distancePassed;

}
