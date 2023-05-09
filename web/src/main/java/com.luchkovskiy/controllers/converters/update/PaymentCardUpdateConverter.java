package com.luchkovskiy.controllers.converters.update;

import com.luchkovskiy.controllers.converters.base.PaymentCardBaseConverter;
import com.luchkovskiy.controllers.requests.update.PaymentCardUpdateRequest;
import com.luchkovskiy.models.PaymentCard;
import com.luchkovskiy.service.PaymentCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentCardUpdateConverter extends PaymentCardBaseConverter<PaymentCardUpdateRequest, PaymentCard> {

    private final PaymentCardService paymentCardService;

    @Override
    public PaymentCard convert(PaymentCardUpdateRequest request) {

        PaymentCard paymentCard = paymentCardService.read(request.getId());

        return doConvert(paymentCard, request);
    }

}
