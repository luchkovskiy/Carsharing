package com.luchkovskiy.controllers.converters.create;

import com.luchkovskiy.controllers.converters.base.SubscriptionBaseConverter;
import com.luchkovskiy.controllers.requests.create.SubscriptionCreateRequest;
import com.luchkovskiy.models.Subscription;
import com.luchkovskiy.models.enums.StatusType;
import com.luchkovskiy.service.SubscriptionLevelService;
import com.luchkovskiy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SubscriptionCreateConverter extends SubscriptionBaseConverter<SubscriptionCreateRequest, Subscription> {

    private final UserService userService;

    private final SubscriptionLevelService subscriptionLevelService;

    @Override
    public Subscription convert(SubscriptionCreateRequest request) {

        Subscription subscription = new Subscription();

        subscription.setUser(userService.read(request.getUserId()));
        subscription.setSubscriptionLevel(subscriptionLevelService.read(request.getLevelId()));
        subscription.setStartTime(LocalDateTime.now());
        subscription.setEndTime(subscription.getStartTime().plusDays(request.getDaysTotal()));
        subscription.setStatus(StatusType.ACTIVE);
        subscription.setTripsAmount(0);
        return doConvert(subscription, request);
    }
}
