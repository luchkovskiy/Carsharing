package com.luchkovskiy.controllers;

import com.luchkovskiy.controllers.exceptions.IllegalRequestException;
import com.luchkovskiy.controllers.requests.create.CarCreateRequest;
import com.luchkovskiy.controllers.requests.update.CarUpdateRequest;
import com.luchkovskiy.models.Car;
import com.luchkovskiy.service.CarService;
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
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    private final ConversionService conversionService;

    @GetMapping("/{id}")
    public ResponseEntity<Car> read(@PathVariable("id") Long id) {
        Car car = carService.read(id);
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> readAll() {
        List<Car> cars = carService.readAll();
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Car> create(@Valid @RequestBody CarCreateRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalRequestException(bindingResult);
        }
        Car car = conversionService.convert(request, Car.class);
        Car createdCar = carService.create(car);
        return new ResponseEntity<>(createdCar, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Car> update(@RequestBody CarUpdateRequest request) {
        Car car = conversionService.convert(request, Car.class);
        Car updatedCar = carService.update(car);
        return new ResponseEntity<>(updatedCar, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        carService.delete(id);
    }

}
