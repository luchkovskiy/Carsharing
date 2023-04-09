package com.luchkovskiy.controllers;


import com.luchkovskiy.controllers.requests.SubscriptionCreateRequest;
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
    public ResponseEntity<Subscription> read(@PathVariable("id") Long id) {
        Subscription subscription = subscriptionService.read(id);
        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> readAll() {
        List<Subscription> subscriptions = subscriptionService.readAll();
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Subscription> create(@RequestBody SubscriptionCreateRequest request) {
        Subscription createdSubscription = subscriptionService.create(getSubscription(request));
        return new ResponseEntity<>(createdSubscription, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<Subscription> update(@RequestBody SubscriptionCreateRequest request) {
        Subscription updatedSubscription = subscriptionService.update(getSubscription(request));
        return new ResponseEntity<>(updatedSubscription, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        subscriptionService.delete(id);
    }

    private static Subscription getSubscription(SubscriptionCreateRequest request) {
        return Subscription.builder()
                .id(request.getId())
                .user(request.getUser())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .status(request.getStatus())
                .tripsAmount(request.getTripsAmount())
                .daysTotal(request.getDaysTotal())
                .levelId(request.getLevelId())
                .build();
    }
}
