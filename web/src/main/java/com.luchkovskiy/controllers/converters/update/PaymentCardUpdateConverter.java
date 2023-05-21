package com.luchkovskiy.controllers.converters.update;

import com.luchkovskiy.controllers.converters.base.PaymentCardBaseConverter;
import com.luchkovskiy.controllers.requests.update.PaymentCardUpdateRequest;
import com.luchkovskiy.models.PaymentCard;
import com.luchkovskiy.security.config.CardConfig;
import com.luchkovskiy.service.PaymentCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentCardUpdateConverter extends PaymentCardBaseConverter<PaymentCardUpdateRequest, PaymentCard> {

    private final PaymentCardService paymentCardService;

    private final PasswordEncoder passwordEncoder;

    private final CardConfig cardConfig;

    @Override
    public PaymentCard convert(PaymentCardUpdateRequest request) {

        PaymentCard paymentCard = paymentCardService.read(request.getId());

        String resultCvv = request.getCvv() + cardConfig.getCvvSalt();
        String encode = passwordEncoder.encode(resultCvv);
        paymentCard.setCvv(encode);

        return doConvert(paymentCard, request);
    }

}
