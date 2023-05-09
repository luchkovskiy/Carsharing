package com.luchkovskiy.controllers.requests.update;

import com.luchkovskiy.controllers.requests.create.SubscriptionLevelCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class SubscriptionLevelUpdateRequest extends SubscriptionLevelCreateRequest {

    private Long id;

}
