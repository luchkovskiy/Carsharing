package com.luchkovskiy.controllers.converters.base;

import com.luchkovskiy.controllers.requests.create.PaymentCardCreateRequest;
import com.luchkovskiy.models.PaymentCard;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;

public abstract class PaymentCardBaseConverter<S, T> implements Converter<S, T> {

    public PaymentCard doConvert(PaymentCard paymentCardForUpdate, PaymentCardCreateRequest request) {

        paymentCardForUpdate.setCardNumber(request.getCardNumber());
        paymentCardForUpdate.setExpirationDate(request.getExpirationDate());
        paymentCardForUpdate.setCvv(request.getCvv());
        paymentCardForUpdate.setChanged(LocalDateTime.now());
        paymentCardForUpdate.setCardholder(request.getCardholder());

        return paymentCardForUpdate;
    }
}
