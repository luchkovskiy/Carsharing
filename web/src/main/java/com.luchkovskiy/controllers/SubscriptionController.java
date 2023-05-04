package com.luchkovskiy.controllers;


import com.luchkovskiy.controllers.requests.create.SubscriptionCreateRequest;
import com.luchkovskiy.controllers.requests.update.*;
import com.luchkovskiy.models.Subscription;
import com.luchkovskiy.service.SubscriptionService;
import com.luchkovskiy.service.UserService;
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
    private final UserService userService;

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
        Subscription subscription = new Subscription();
        subscription.setUser(userService.read(request.getUserId()));
        subscription.setStartTime(request.getStartTime());
        subscription.setEndTime(request.getEndTime());
        subscription.setStatus(request.getStatus());
        subscription.setTripsAmount(request.getTripsAmount());
        subscription.setDaysTotal(request.getDaysTotal());
        subscription.setLevelId(request.getLevelId());
        Subscription createdSubscription = subscriptionService.create(subscription);
        return new ResponseEntity<>(createdSubscription, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Subscription> update(@RequestBody SubscriptionUpdateRequest request) {
        Subscription readedSubscription = subscriptionService.read(request.getId());
        readedSubscription.setId(request.getId());
        readedSubscription.setUser(userService.read(request.getUserId()));
        readedSubscription.setStartTime(request.getStartTime());
        readedSubscription.setEndTime(request.getEndTime());
        readedSubscription.setStatus(request.getStatus());
        readedSubscription.setTripsAmount(request.getTripsAmount());
        readedSubscription.setDaysTotal(request.getDaysTotal());
        readedSubscription.setLevelId(request.getLevelId());
        Subscription updatedSubscription = subscriptionService.update(readedSubscription);
        return new ResponseEntity<>(updatedSubscription, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        subscriptionService.delete(id);
    }

}
