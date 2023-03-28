package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.PaymentCard;
import com.luchkovskiy.repository.PaymentCardRepository;
import com.luchkovskiy.service.PaymentCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentCardServiceImpl implements PaymentCardService {

    private final PaymentCardRepository paymentCardRepository;

    @Override
    public PaymentCard read(Long id) {
        return paymentCardRepository.read(id);
    }

    @Override
    public List<PaymentCard> readAll() {
        return paymentCardRepository.readAll();
    }

    @Override
    public PaymentCard create(PaymentCard object) {
        return paymentCardRepository.create(object);
    }

    @Override
    public PaymentCard update(PaymentCard object) {
        return paymentCardRepository.update(object);
    }

    @Override
    public void delete(Long id) {
        paymentCardRepository.delete(id);
    }
}
