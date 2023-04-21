package com.luchkovskiy.controllers.requests.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscriptionCreateRequest {

    private Long userId;
    private Timestamp startTime;
    private Timestamp endTime;
    private String status;
    private Integer tripsAmount;
    private Integer daysTotal;
    private Integer levelId;

}

