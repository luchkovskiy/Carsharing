package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.PaymentCard;
import com.luchkovskiy.repository.PaymentCardRepository;
import com.luchkovskiy.service.PaymentCardService;
import com.luchkovskiy.service.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentCardServiceImpl implements PaymentCardService {

    private final PaymentCardRepository paymentCardRepository;

    @Override
    public PaymentCard read(Long id) {
        return paymentCardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Payment card not found!"));
    }

    @Override
    public List<PaymentCard> readAll() {
        return paymentCardRepository.findAll();
    }

    @Override
    public PaymentCard create(PaymentCard object) {
        return paymentCardRepository.save(object);
    }

    @Override
    public PaymentCard update(PaymentCard object) {
        if (!paymentCardRepository.existsById(object.getId()))
            throw new EntityNotFoundException("Payment card not found!");
        return paymentCardRepository.save(object);
    }

    @Override
    public void delete(Long id) {
        if (!paymentCardRepository.existsById(id))
            throw new EntityNotFoundException("Payment card not found!");
        paymentCardRepository.deleteById(id);
    }
}
