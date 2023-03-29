package com.luchkovskiy.service;

import com.luchkovskiy.models.Subscription;

public interface SubscriptionService extends CRUDService<Long, Subscription> {

    boolean checkIdExist(Long id);

}
