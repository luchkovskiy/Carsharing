package com.luchkovskiy.controllers.requests.update;

import com.luchkovskiy.controllers.requests.create.SubscriptionCreateRequest;
import com.luchkovskiy.models.enums.StatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Validated
@Schema(description = "Subscription information to update in database")
public class SubscriptionUpdateRequest extends SubscriptionCreateRequest {

    @NotNull
    @Min(1)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer", description = "Id of the subscription")
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-02-22T17:24:01", type = "date-time",
            description = "The time when the subscription started")
    private LocalDateTime startTime;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-02-24T17:24:01", type = "date-time",
            description = "The time when the subscription ended")
    private LocalDateTime endTime;

    @NotNull
    @Min(0)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "5", type = "integer", description = "Amount of trips during the current subscription")
    private Integer tripsAmount;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "ACTIVE", type = "statusType", description = "Status of the subscription")
    private StatusType status;

}
