package com.luchkovskiy.controllers;

import com.luchkovskiy.controllers.requests.create.CarRentInfoCreateRequest;
import com.luchkovskiy.controllers.requests.update.CarRentInfoUpdateRequest;
import com.luchkovskiy.models.CarRentInfo;
import com.luchkovskiy.service.CarRentInfoService;
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
@RequestMapping("/cars_info")
@RequiredArgsConstructor
@Validated
public class CarRentInfoController {

    private final CarRentInfoService carRentInfoService;

    private final ConversionService conversionService;

    @GetMapping("/{id}")
    public ResponseEntity<CarRentInfo> read(@PathVariable("id") @NotEmpty @Min(1) Long id, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        CarRentInfo carRentInfo = carRentInfoService.read(id);
        return new ResponseEntity<>(carRentInfo, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> readAll() {
        List<CarRentInfo> carRentInfo = carRentInfoService.readAll();
        return new ResponseEntity<>(carRentInfo, HttpStatus.OK);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @PostMapping
    public ResponseEntity<CarRentInfo> create(@Valid @RequestBody CarRentInfoCreateRequest request, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        CarRentInfo carRentInfo = conversionService.convert(request, CarRentInfo.class);
        CarRentInfo createdCarRentInfo = carRentInfoService.create(carRentInfo);
        return new ResponseEntity<>(createdCarRentInfo, HttpStatus.CREATED);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @PutMapping
    public ResponseEntity<Object> update(@Valid @RequestBody CarRentInfoUpdateRequest request, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        CarRentInfo carRentInfo = conversionService.convert(request, CarRentInfo.class);
        CarRentInfo updatedCarRentInfo = carRentInfoService.update(carRentInfo);
        return new ResponseEntity<>(updatedCarRentInfo, HttpStatus.OK);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") @NotEmpty @Min(1) Long id, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        carRentInfoService.delete(id);
    }

}
