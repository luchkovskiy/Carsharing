package com.luchkovskiy.controllers.requests;

import com.luchkovskiy.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscriptionCreateRequest {

    private Long id;
    private User user;
    private Timestamp startTime;
    private Timestamp endTime;
    private String status;
    private Integer tripsAmount;
    private Integer daysTotal;
    private Integer levelId;

}

