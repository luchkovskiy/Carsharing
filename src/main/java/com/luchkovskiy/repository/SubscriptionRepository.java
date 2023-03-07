package com.luchkovskiy.repository;

import com.luchkovskiy.domain.Subscription;

public interface SubscriptionRepository extends CRUDRepository<Long, Subscription> {

    boolean checkIdValid(Long id);

}
