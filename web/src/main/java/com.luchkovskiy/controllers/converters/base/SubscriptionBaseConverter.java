package com.luchkovskiy.controllers.converters.base;

import com.luchkovskiy.controllers.requests.create.SubscriptionCreateRequest;
import com.luchkovskiy.models.Subscription;
import org.springframework.core.convert.converter.Converter;

public abstract class SubscriptionBaseConverter<S, T> implements Converter<S, T> {

    public Subscription doConvert(Subscription subscriptionForUpdate, SubscriptionCreateRequest request) {

        subscriptionForUpdate.setDaysTotal(request.getDaysTotal());

        return subscriptionForUpdate;
    }

}
