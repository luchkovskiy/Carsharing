package com.luchkovskiy.controllers;

import com.luchkovskiy.controllers.requests.create.CarCreateRequest;
import com.luchkovskiy.controllers.requests.update.*;
import com.luchkovskiy.models.Car;
import com.luchkovskiy.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

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
    public ResponseEntity<Car> create(@RequestBody CarCreateRequest request) {
        Car car = new Car();
        car.setBrand(request.getBrand());
        car.setModel(request.getModel());
        car.setMaxSpeed(request.getMaxSpeed());
        car.setColor(request.getColor());
        car.setReleaseYear(request.getReleaseYear());
        car.setGearboxType(request.getGearboxType());
        car.setSitsAmount(request.getSitsAmount());
        car.setClassId(request.getClassId());
        car.setGasConsumption(request.getGasConsumption());
        car.setLicensePlateNumber(request.getLicensePlateNumber());
        Car createdCar = carService.create(car);
        return new ResponseEntity<>(createdCar, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Car> update(@RequestBody CarUpdateRequest request) {
        Car readedCar = carService.read(request.getId());
        readedCar.setId(request.getId());
        readedCar.setBrand(request.getBrand());
        readedCar.setModel(request.getModel());
        readedCar.setMaxSpeed(request.getMaxSpeed());
        readedCar.setColor(request.getColor());
        readedCar.setReleaseYear(request.getReleaseYear());
        readedCar.setGearboxType(request.getGearboxType());
        readedCar.setSitsAmount(request.getSitsAmount());
        readedCar.setClassId(request.getClassId());
        readedCar.setGasConsumption(request.getGasConsumption());
        readedCar.setLicensePlateNumber(request.getLicensePlateNumber());
        Car updatedCar = carService.update(readedCar);
        return new ResponseEntity<>(updatedCar, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        carService.delete(id);
    }

}
