package com.luchkovskiy.repository;

import com.luchkovskiy.models.Subscription;

public interface SubscriptionRepository extends CRUDRepository<Long, Subscription> {

    boolean checkIdValid(Long id);

}
