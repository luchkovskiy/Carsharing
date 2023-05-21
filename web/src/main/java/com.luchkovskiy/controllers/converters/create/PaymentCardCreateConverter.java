package com.luchkovskiy.controllers.converters.create;

import com.luchkovskiy.controllers.converters.base.PaymentCardBaseConverter;
import com.luchkovskiy.controllers.requests.create.PaymentCardCreateRequest;
import com.luchkovskiy.models.PaymentCard;
import com.luchkovskiy.security.config.CardConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentCardCreateConverter extends PaymentCardBaseConverter<PaymentCardCreateRequest, PaymentCard> {

    private final PasswordEncoder passwordEncoder;

    private final CardConfig cardConfig;


    @Override
    public PaymentCard convert(PaymentCardCreateRequest request) {

        PaymentCard paymentCard = new PaymentCard();

        String resultCvv = request.getCvv() + cardConfig.getCvvSalt();
        String encode = passwordEncoder.encode(resultCvv);
        paymentCard.setCvv(encode);

        return doConvert(paymentCard, request);
    }

}
