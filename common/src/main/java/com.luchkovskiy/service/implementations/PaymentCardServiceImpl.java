package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.PaymentCard;
import com.luchkovskiy.repository.PaymentCardRepository;
import com.luchkovskiy.service.PaymentCardService;
import com.luchkovskiy.service.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentCardServiceImpl implements PaymentCardService {

    private final PaymentCardRepository paymentCardRepository;

    private final EntityManager entityManager;

    @Override
    public PaymentCard findById(Long id) {
        return paymentCardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Payment card not found!"));
    }

    @Override
    public List<PaymentCard> findAll() {
        return paymentCardRepository.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public PaymentCard create(PaymentCard object) {
        cardNumberCheck(object);
        return paymentCardRepository.save(object);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public PaymentCard update(PaymentCard object) {
        entityManager.clear();
        if (!paymentCardRepository.existsById(object.getId()))
            throw new EntityNotFoundException("Payment card not found!");
        cardNumberCheck(object);
        return paymentCardRepository.save(object);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public void delete(Long id) {
        if (!paymentCardRepository.existsById(id))
            throw new EntityNotFoundException("Payment card not found!");
        paymentCardRepository.deletePaymentCard(id);
    }

    private void cardNumberCheck(PaymentCard paymentCard) {
        if (paymentCard.getId() != null && paymentCardRepository.findById(paymentCard.getId()).get().getCardNumber().
                equals(paymentCard.getCardNumber()))
            return;
        if (paymentCardRepository.existsByCardNumber(paymentCard.getCardNumber())) {
            throw new RuntimeException("This card number is already exist in database");
        }
    }
}
