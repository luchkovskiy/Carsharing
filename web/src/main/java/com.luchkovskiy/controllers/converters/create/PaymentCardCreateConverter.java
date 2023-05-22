package com.luchkovskiy.controllers.converters.create;

import com.luchkovskiy.controllers.converters.base.PaymentCardBaseConverter;
import com.luchkovskiy.controllers.requests.create.PaymentCardCreateRequest;
import com.luchkovskiy.models.PaymentCard;
import com.luchkovskiy.util.EncryptionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentCardCreateConverter extends PaymentCardBaseConverter<PaymentCardCreateRequest, PaymentCard> {

    private final EncryptionUtils encryptionUtils;


    @Override
    public PaymentCard convert(PaymentCardCreateRequest request) {

        PaymentCard paymentCard = new PaymentCard();

        String resultCvv = null;
        try {
            resultCvv = encryptionUtils.encrypt(request.getCvv());
        } catch (Exception e) {
            e.printStackTrace();
        }
        paymentCard.setCvv(resultCvv);

        return doConvert(paymentCard, request);
    }

}
