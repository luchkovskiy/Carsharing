package com.luchkovskiy.controllers.converters.create;


import com.luchkovskiy.controllers.converters.base.SubscriptionLevelBaseConverter;
import com.luchkovskiy.controllers.requests.create.SubscriptionLevelCreateRequest;
import com.luchkovskiy.models.SubscriptionLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SubscriptionLevelCreateConverter extends SubscriptionLevelBaseConverter<SubscriptionLevelCreateRequest, SubscriptionLevel> {

    @Override
    public SubscriptionLevel convert(SubscriptionLevelCreateRequest request) {

        SubscriptionLevel subscriptionLevel = new SubscriptionLevel();
        subscriptionLevel.setCreated(LocalDateTime.now());

        return doConvert(subscriptionLevel, request);
    }
}
