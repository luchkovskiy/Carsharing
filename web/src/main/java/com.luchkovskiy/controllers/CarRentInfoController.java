package com.luchkovskiy.controllers;

import com.luchkovskiy.controllers.requests.create.CarRentInfoCreateRequest;
import com.luchkovskiy.controllers.requests.update.*;
import com.luchkovskiy.models.CarRentInfo;
import com.luchkovskiy.service.CarRentInfoService;
import com.luchkovskiy.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars_info")
@RequiredArgsConstructor
public class CarRentInfoController {

    private final CarRentInfoService carRentInfoService;
    private final CarService carService;

    @GetMapping("/{id}")
    public ResponseEntity<CarRentInfo> read(@PathVariable("id") Long id) {
        CarRentInfo carRentInfo = carRentInfoService.read(id);
        return new ResponseEntity<>(carRentInfo, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> readAll() {
        List<CarRentInfo> carRentInfo = carRentInfoService.readAll();
        return new ResponseEntity<>(carRentInfo, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CarRentInfo> create(@RequestBody CarRentInfoCreateRequest request) {
        CarRentInfo carRentInfo = new CarRentInfo();
        carRentInfo.setCar(carService.read(request.getCarId()));
        carRentInfo.setGasRemaining(request.getGasRemaining());
        carRentInfo.setRepairing(request.getRepairing());
        carRentInfo.setCurrentLocation(request.getCurrentLocation());
        carRentInfo.setCondition(request.getCondition());
        CarRentInfo createdCarRentInfo = carRentInfoService.create(carRentInfo);
        return new ResponseEntity<>(createdCarRentInfo, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody CarRentInfoUpdateRequest request) {
        CarRentInfo readedCarRentInfo = carRentInfoService.read(request.getId());
        readedCarRentInfo.setId(request.getId());
        readedCarRentInfo.setCar(carService.read(request.getCarId()));
        readedCarRentInfo.setGasRemaining(request.getGasRemaining());
        readedCarRentInfo.setRepairing(request.getRepairing());
        readedCarRentInfo.setCurrentLocation(request.getCurrentLocation());
        readedCarRentInfo.setCondition(request.getCondition());
        CarRentInfo updatedCarRentInfo = carRentInfoService.update(readedCarRentInfo);
        return new ResponseEntity<>(updatedCarRentInfo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        carRentInfoService.delete(id);
    }

}
