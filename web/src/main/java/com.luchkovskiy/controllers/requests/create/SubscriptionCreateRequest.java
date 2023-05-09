package com.luchkovskiy.controllers.requests.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscriptionCreateRequest {

    private Long userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private Integer tripsAmount;
    private Integer daysTotal;
    private Long levelId;

}

