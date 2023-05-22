package com.luchkovskiy.controllers.converters.update;

import com.luchkovskiy.controllers.converters.base.PaymentCardBaseConverter;
import com.luchkovskiy.controllers.requests.update.PaymentCardUpdateRequest;
import com.luchkovskiy.models.PaymentCard;
import com.luchkovskiy.service.PaymentCardService;
import com.luchkovskiy.util.EncryptionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentCardUpdateConverter extends PaymentCardBaseConverter<PaymentCardUpdateRequest, PaymentCard> {

    private final PaymentCardService paymentCardService;

    private final EncryptionUtils encryptionUtils;

    @Override
    public PaymentCard convert(PaymentCardUpdateRequest request) {

        PaymentCard paymentCard = paymentCardService.findById(request.getId());

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
