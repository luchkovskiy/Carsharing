package com.luchkovskiy.controllers;

import com.luchkovskiy.controllers.requests.create.PaymentCardCreateRequest;
import com.luchkovskiy.controllers.requests.update.*;
import com.luchkovskiy.models.PaymentCard;
import com.luchkovskiy.service.PaymentCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment_cards")
@RequiredArgsConstructor
public class PaymentCardController {

    private final PaymentCardService paymentCardService;

    @GetMapping("/{id}")
    public ResponseEntity<PaymentCard> read(@PathVariable("id") Long id) {
        PaymentCard paymentCard = paymentCardService.read(id);
        return new ResponseEntity<>(paymentCard, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> readAll() {
        List<PaymentCard> paymentCards = paymentCardService.readAll();
        return new ResponseEntity<>(paymentCards, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PaymentCard> create(@RequestBody PaymentCardCreateRequest request) {
        PaymentCard paymentCard = new PaymentCard();
        paymentCard.setCardNumber(request.getCardNumber());
        paymentCard.setExpirationDate(request.getExpirationDate());
        paymentCard.setCvv(request.getCvv());
        paymentCard.setCardholder(request.getCardholder());
        PaymentCard createdPaymentCard = paymentCardService.create(paymentCard);
        return new ResponseEntity<>(createdPaymentCard, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<PaymentCard> update(@RequestBody PaymentCardUpdateRequest request) {
        PaymentCard readedPaymentCard = paymentCardService.read(request.getId());
        readedPaymentCard.setId(request.getId());
        readedPaymentCard.setCardNumber(request.getCardNumber());
        readedPaymentCard.setExpirationDate(request.getExpirationDate());
        readedPaymentCard.setCvv(request.getCvv());
        readedPaymentCard.setCardholder(request.getCardholder());
        PaymentCard updatedPaymentCard = paymentCardService.update(readedPaymentCard);
        return new ResponseEntity<>(updatedPaymentCard, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        paymentCardService.delete(id);
    }

}
