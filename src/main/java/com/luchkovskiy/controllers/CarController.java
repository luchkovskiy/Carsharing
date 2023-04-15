package com.luchkovskiy.controllers;

import com.luchkovskiy.controllers.requests.CarCreateRequest;
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
        Car createdCar = carService.create(getCar(request));
        return new ResponseEntity<>(createdCar, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<Car> update(@RequestBody CarCreateRequest request) {
        Car updatedCar = carService.update(getCar(request));
        return new ResponseEntity<>(updatedCar, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        carService.delete(id);
    }

    private Car getCar(CarCreateRequest request) {
        return Car.builder()
                .id(request.getId())
                .brand(request.getBrand())
                .model(request.getModel())
                .maxSpeed(request.getMaxSpeed())
                .color(request.getColor())
                .releaseYear(request.getReleaseYear())
                .gearboxType(request.getGearboxType())
                .sitsAmount(request.getSitsAmount())
                .classId(request.getClassId())
                .gasConsumption(request.getGasConsumption())
                .licensePlateNumber(request.getLicensePlateNumber())
                .build();
    }
}
