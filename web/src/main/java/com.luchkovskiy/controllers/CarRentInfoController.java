package com.luchkovskiy.controllers;

import com.luchkovskiy.controllers.exceptions.IllegalRequestException;
import com.luchkovskiy.controllers.requests.create.CarRentInfoCreateRequest;
import com.luchkovskiy.controllers.requests.update.CarRentInfoUpdateRequest;
import com.luchkovskiy.models.CarRentInfo;
import com.luchkovskiy.service.CarRentInfoService;
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
@RequestMapping("/cars_info")
@RequiredArgsConstructor
public class CarRentInfoController {

    private final CarRentInfoService carRentInfoService;

    private final ConversionService conversionService;

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
    public ResponseEntity<CarRentInfo> create(@Valid @RequestBody CarRentInfoCreateRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalRequestException(bindingResult);
        }
        CarRentInfo carRentInfo = conversionService.convert(request, CarRentInfo.class);
        CarRentInfo createdCarRentInfo = carRentInfoService.create(carRentInfo);
        return new ResponseEntity<>(createdCarRentInfo, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody CarRentInfoUpdateRequest request) {
        CarRentInfo carRentInfo = conversionService.convert(request, CarRentInfo.class);
        CarRentInfo updatedCarRentInfo = carRentInfoService.update(carRentInfo);
        return new ResponseEntity<>(updatedCarRentInfo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        carRentInfoService.delete(id);
    }

}
