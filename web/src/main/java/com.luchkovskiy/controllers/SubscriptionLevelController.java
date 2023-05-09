package com.luchkovskiy.controllers;

import com.luchkovskiy.controllers.exceptions.IllegalRequestException;
import com.luchkovskiy.controllers.requests.create.SubscriptionLevelCreateRequest;
import com.luchkovskiy.controllers.requests.update.SubscriptionLevelUpdateRequest;
import com.luchkovskiy.models.SubscriptionLevel;
import com.luchkovskiy.service.SubscriptionLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/subscription_levels")
@RequiredArgsConstructor
public class SubscriptionLevelController {

    private final SubscriptionLevelService subscriptionLevelService;

    private final ConversionService conversionService;

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionLevel> read(@PathVariable("id") Long id) {
        SubscriptionLevel subscriptionLevel = subscriptionLevelService.read(id);
        return new ResponseEntity<>(subscriptionLevel, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> readAll() {
        List<SubscriptionLevel> subscriptionLevels = subscriptionLevelService.readAll();
        return new ResponseEntity<>(subscriptionLevels, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SubscriptionLevel> create(@Valid @RequestBody SubscriptionLevelCreateRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalRequestException(bindingResult);
        }
        SubscriptionLevel subscriptionLevel = conversionService.convert(request, SubscriptionLevel.class);
        SubscriptionLevel createdSubscriptionLevel = subscriptionLevelService.create(subscriptionLevel);
        return new ResponseEntity<>(createdSubscriptionLevel, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<SubscriptionLevel> update(@RequestBody SubscriptionLevelUpdateRequest request) {
        SubscriptionLevel subscriptionLevel = conversionService.convert(request, SubscriptionLevel.class);
        SubscriptionLevel updatedSubscriptionLevel = subscriptionLevelService.update(subscriptionLevel);
        return new ResponseEntity<>(updatedSubscriptionLevel, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        subscriptionLevelService.delete(id);
    }

}
