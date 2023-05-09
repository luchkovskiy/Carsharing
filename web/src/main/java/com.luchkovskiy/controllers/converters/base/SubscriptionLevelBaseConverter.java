package com.luchkovskiy.controllers.converters.base;

import com.luchkovskiy.controllers.requests.create.SubscriptionLevelCreateRequest;
import com.luchkovskiy.models.SubscriptionLevel;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;

public abstract class SubscriptionLevelBaseConverter<S, T> implements Converter<S, T> {


    public SubscriptionLevel doConvert(SubscriptionLevel subscriptionLevelForUpdate, SubscriptionLevelCreateRequest request) {

        subscriptionLevelForUpdate.setAccessLevel(request.getAccessLevel());
        subscriptionLevelForUpdate.setPricePerDay(request.getPricePerDay());
        subscriptionLevelForUpdate.setName(request.getName());
        subscriptionLevelForUpdate.setChanged(LocalDateTime.now());

        return subscriptionLevelForUpdate;
    }

}
