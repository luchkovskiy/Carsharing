package com.luchkovskiy.controllers;

import com.luchkovskiy.controllers.requests.create.PaymentCardCreateRequest;
import com.luchkovskiy.controllers.requests.update.PaymentCardUpdateRequest;
import com.luchkovskiy.models.PaymentCard;
import com.luchkovskiy.service.PaymentCardService;
import com.luchkovskiy.util.ExceptionChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping("/payment_cards")
@RequiredArgsConstructor
@Validated
public class PaymentCardController {

    private final PaymentCardService paymentCardService;

    private final ConversionService conversionService;

    @GetMapping("/{id}")
    public ResponseEntity<PaymentCard> read(@PathVariable("id") @NotEmpty @Min(1) Long id, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        PaymentCard paymentCard = paymentCardService.read(id);
        return new ResponseEntity<>(paymentCard, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> readAll() {
        List<PaymentCard> paymentCards = paymentCardService.readAll();
        return new ResponseEntity<>(paymentCards, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PaymentCard> create(@Valid @RequestBody PaymentCardCreateRequest request, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        PaymentCard paymentCard = conversionService.convert(request, PaymentCard.class);
        PaymentCard createdPaymentCard = paymentCardService.create(paymentCard);
        return new ResponseEntity<>(createdPaymentCard, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<PaymentCard> update(@Valid @RequestBody PaymentCardUpdateRequest request, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        PaymentCard paymentCard = conversionService.convert(request, PaymentCard.class);
        PaymentCard updatedPaymentCard = paymentCardService.update(paymentCard);
        return new ResponseEntity<>(updatedPaymentCard, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") @NotEmpty @Min(1) Long id, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        paymentCardService.delete(id);
    }

}
