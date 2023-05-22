package com.luchkovskiy.controllers.converters.update;


import com.luchkovskiy.controllers.converters.base.SubscriptionLevelBaseConverter;
import com.luchkovskiy.controllers.requests.update.SubscriptionLevelUpdateRequest;
import com.luchkovskiy.models.SubscriptionLevel;
import com.luchkovskiy.service.SubscriptionLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscriptionLevelUpdateConverter extends SubscriptionLevelBaseConverter<SubscriptionLevelUpdateRequest, SubscriptionLevel> {

    private final SubscriptionLevelService subscriptionLevelService;

    @Override
    public SubscriptionLevel convert(SubscriptionLevelUpdateRequest request) {

        SubscriptionLevel subscriptionLevel = subscriptionLevelService.findById(request.getId());

        return doConvert(subscriptionLevel, request);
    }
}
