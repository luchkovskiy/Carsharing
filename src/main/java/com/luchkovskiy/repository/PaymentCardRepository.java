package com.luchkovskiy.repository;

import com.luchkovskiy.models.PaymentCard;

public interface PaymentCardRepository extends CRUDRepository<Long, PaymentCard> {

    boolean checkIdValid(Long id);

}
