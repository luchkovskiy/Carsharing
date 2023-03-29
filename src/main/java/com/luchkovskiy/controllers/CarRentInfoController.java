package com.luchkovskiy.controllers;

import com.luchkovskiy.models.CarRentInfo;
import com.luchkovskiy.service.CarRentInfoService;
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

    @GetMapping("/{id}")
    public ResponseEntity<Object> read(@PathVariable("id") Long id) {
        CarRentInfo carRentInfo = carRentInfoService.read(id);
        return new ResponseEntity<>(carRentInfo, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> readAll() {
        List<CarRentInfo> carRentInfo = carRentInfoService.readAll();
        return new ResponseEntity<>(carRentInfo, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CarRentInfo carRentInfo) {
        CarRentInfo createdCarRentInfo = carRentInfoService.create(carRentInfo);
        return new ResponseEntity<>(createdCarRentInfo, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<Object> update(@RequestBody CarRentInfo carRentInfo) {
        CarRentInfo updatedCarRentInfo = carRentInfoService.update(carRentInfo);
        return new ResponseEntity<>(updatedCarRentInfo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        carRentInfoService.delete(id);
    }

}
