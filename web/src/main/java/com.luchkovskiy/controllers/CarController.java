package com.luchkovskiy.controllers;

import com.luchkovskiy.controllers.requests.create.CarCreateRequest;
import com.luchkovskiy.controllers.requests.update.CarUpdateRequest;
import com.luchkovskiy.models.Car;
import com.luchkovskiy.service.CarService;
import com.luchkovskiy.util.ExceptionChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping("/cars")
@RequiredArgsConstructor
@Validated
public class CarController {

    private final CarService carService;

    private final ConversionService conversionService;

    @GetMapping("/{id}")
    public ResponseEntity<Car> read(@PathVariable("id") @NotEmpty @Min(1) Long id, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        Car car = carService.read(id);
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> readAll() {
        List<Car> cars = carService.readAll();
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @PostMapping
    public ResponseEntity<Car> create(@Valid @RequestBody CarCreateRequest request, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        Car car = conversionService.convert(request, Car.class);
        Car createdCar = carService.create(car);
        return new ResponseEntity<>(createdCar, HttpStatus.CREATED);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @PutMapping
    public ResponseEntity<Car> update(@Valid @RequestBody CarUpdateRequest request, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        Car car = conversionService.convert(request, Car.class);
        Car updatedCar = carService.update(car);
        return new ResponseEntity<>(updatedCar, HttpStatus.OK);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") @NotEmpty @Min(1) Long id, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        carService.delete(id);
    }

}
