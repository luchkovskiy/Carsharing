package com.luchkovskiy.controllers;

import com.luchkovskiy.controllers.requests.PaymentCardCreateRequest;
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
        PaymentCard createdPaymentCard = paymentCardService.create(getPaymentCard(request));
        return new ResponseEntity<>(createdPaymentCard, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<PaymentCard> update(@RequestBody PaymentCardCreateRequest request) {
        PaymentCard updatedPaymentCard = paymentCardService.update(getPaymentCard(request));
        return new ResponseEntity<>(updatedPaymentCard, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        paymentCardService.delete(id);
    }

    private static PaymentCard getPaymentCard(PaymentCardCreateRequest request) {
        return PaymentCard.builder()
                .id(request.getId())
                .cardNumber(request.getCardNumber())
                .expirationDate(request.getExpirationDate())
                .cvv(request.getCvv())
                .cardholder(request.getCardholder())
                .build();
    }
}
