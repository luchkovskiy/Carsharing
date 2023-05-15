package com.luchkovskiy.controllers.requests.create;

import com.luchkovskiy.models.StatusType;
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
@Schema(description = "Subscription information to save in database")
public class SubscriptionCreateRequest {

    @NotNull
    @Min(1)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer", description = "User's Id in database")
    private Long userId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-02-22T17:24:01", type = "date-time",
            description = "The time when the subscription started")
    private LocalDateTime startTime;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-02-24T17:24:01", type = "date-time",
            description = "The time when the subscription ended")
    private LocalDateTime endTime;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "ACTIVE", type = "statusType", description = "Status of the subscription")
    private StatusType status;

    @NotNull
    @Min(0)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "5", type = "integer", description = "Amount of trips during the current subscription")
    private Integer tripsAmount;

    @NotNull
    @Min(1)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2", type = "integer", description = "Amount of days the subscription was active")
    private Integer daysTotal;

    @NotNull
    @Min(1)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer", description = "Subscription's level Id")
    private Long levelId;

}

