package com.luchkovskiy.controllers.converters.create;

import com.luchkovskiy.controllers.converters.base.PaymentCardBaseConverter;
import com.luchkovskiy.controllers.requests.create.PaymentCardCreateRequest;
import com.luchkovskiy.models.PaymentCard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PaymentCardCreateConverter extends PaymentCardBaseConverter<PaymentCardCreateRequest, PaymentCard> {

    @Override
    public PaymentCard convert(PaymentCardCreateRequest request) {

        PaymentCard paymentCard = new PaymentCard();


        paymentCard.setCreated(LocalDateTime.now());


        return doConvert(paymentCard, request);
    }

}
