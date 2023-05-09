package com.luchkovskiy.controllers.requests.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscriptionLevelCreateRequest {

    private Integer accessLevel;

    private Float pricePerDay;

    private String name;

}
