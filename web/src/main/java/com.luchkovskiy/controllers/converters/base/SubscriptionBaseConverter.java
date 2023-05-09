package com.luchkovskiy.controllers.converters.base;

import com.luchkovskiy.controllers.requests.create.SubscriptionCreateRequest;
import com.luchkovskiy.models.Subscription;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;

public abstract class SubscriptionBaseConverter<S, T> implements Converter<S, T> {

    public Subscription doConvert(Subscription subscriptionForUpdate, SubscriptionCreateRequest request) {

        subscriptionForUpdate.setStartTime(request.getStartTime());
        subscriptionForUpdate.setEndTime(request.getEndTime());
        subscriptionForUpdate.setStatus(request.getStatus());
        subscriptionForUpdate.setTripsAmount(request.getTripsAmount());
        subscriptionForUpdate.setDaysTotal(request.getDaysTotal());
        subscriptionForUpdate.setChanged(LocalDateTime.now());


        return subscriptionForUpdate;
    }

}
