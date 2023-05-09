package com.luchkovskiy.controllers.converters.update;

import com.luchkovskiy.controllers.converters.base.SubscriptionBaseConverter;
import com.luchkovskiy.controllers.requests.update.SubscriptionUpdateRequest;
import com.luchkovskiy.models.Subscription;
import com.luchkovskiy.service.SubscriptionLevelService;
import com.luchkovskiy.service.SubscriptionService;
import com.luchkovskiy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscriptionUpdateConverter extends SubscriptionBaseConverter<SubscriptionUpdateRequest, Subscription> {

    private final UserService userService;

    private final SubscriptionService subscriptionService;

    private final SubscriptionLevelService subscriptionLevelService;

    @Override
    public Subscription convert(SubscriptionUpdateRequest request) {

        Subscription subscription = subscriptionService.read(request.getId());
        subscription.setUser(userService.read(request.getUserId()));
        subscription.setSubscriptionLevel(subscriptionLevelService.read(request.getLevelId()));
        return doConvert(subscription, request);
    }

}
