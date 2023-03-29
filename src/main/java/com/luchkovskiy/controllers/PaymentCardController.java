package com.luchkovskiy.controllers;

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
    public ResponseEntity<Object> read(@PathVariable("id") Long id) {
        PaymentCard paymentCard = paymentCardService.read(id);
        return new ResponseEntity<>(paymentCard, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> readAll() {
        List<PaymentCard> paymentCards = paymentCardService.readAll();
        return new ResponseEntity<>(paymentCards, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody PaymentCard paymentCard) {
        PaymentCard createdPaymentCard = paymentCardService.create(paymentCard);
        return new ResponseEntity<>(createdPaymentCard, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<Object> update(@RequestBody PaymentCard paymentCard) {
        PaymentCard updatedPaymentCard = paymentCardService.update(paymentCard);
        return new ResponseEntity<>(updatedPaymentCard, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        paymentCardService.delete(id);
    }

}
