package com.luchkovskiy.controllers.requests.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Validated
public class SubscriptionCreateRequest {

    @NotEmpty
    private Long userId;

    @NotEmpty
    @PastOrPresent
    private LocalDateTime startTime;

    @NotEmpty
    @PastOrPresent
    private LocalDateTime endTime;

    @NotBlank
    private String status;

    @NotEmpty
    private Integer tripsAmount;

    @NotEmpty
    private Integer daysTotal;

    @NotEmpty
    private Long levelId;

}

