package com.luchkovskiy.controllers;


import com.luchkovskiy.models.Subscription;
import com.luchkovskiy.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> read(@PathVariable("id") Long id) {
        Subscription subscription = subscriptionService.read(id);
        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> readAll() {
        List<Subscription> subscriptions = subscriptionService.readAll();
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Subscription subscription) {
        Subscription createdSubscription = subscriptionService.create(subscription);
        return new ResponseEntity<>(createdSubscription, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<Object> update(@RequestBody Subscription subscription) {
        Subscription updatedSubscription = subscriptionService.update(subscription);
        return new ResponseEntity<>(updatedSubscription, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        subscriptionService.delete(id);
    }

}
