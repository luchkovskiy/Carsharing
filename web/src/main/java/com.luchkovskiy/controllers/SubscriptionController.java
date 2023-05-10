package com.luchkovskiy.controllers;


import com.luchkovskiy.controllers.requests.create.SubscriptionCreateRequest;
import com.luchkovskiy.controllers.requests.update.SubscriptionUpdateRequest;
import com.luchkovskiy.models.Subscription;
import com.luchkovskiy.service.SubscriptionService;
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
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
@Validated
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    private final ConversionService conversionService;

    @GetMapping("/{id}")
    public ResponseEntity<Subscription> read(@PathVariable("id") @NotEmpty @Min(1) Long id, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        Subscription subscription = subscriptionService.read(id);
        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> readAll() {
        List<Subscription> subscriptions = subscriptionService.readAll();
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Subscription> create(@Valid @RequestBody SubscriptionCreateRequest request, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        Subscription subscription = conversionService.convert(request, Subscription.class);
        Subscription createdSubscription = subscriptionService.create(subscription);
        return new ResponseEntity<>(createdSubscription, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Subscription> update(@Valid @RequestBody SubscriptionUpdateRequest request, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        Subscription subscription = conversionService.convert(request, Subscription.class);
        Subscription updatedSubscription = subscriptionService.update(subscription);
        return new ResponseEntity<>(updatedSubscription, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") @NotEmpty @Min(1) Long id, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        subscriptionService.delete(id);
    }

}
