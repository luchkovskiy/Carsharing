package com.luchkovskiy.controllers;

import com.luchkovskiy.controllers.requests.create.CarClassCreateRequest;
import com.luchkovskiy.controllers.requests.update.CarClassUpdateRequest;
import com.luchkovskiy.models.CarClass;
import com.luchkovskiy.service.CarClassService;
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
@RequestMapping("/car_classes")
@RequiredArgsConstructor
@Validated
public class CarClassController {

    private final CarClassService carClassService;

    private final ConversionService conversionService;

    @GetMapping("/{id}")
    public ResponseEntity<CarClass> read(@PathVariable("id") @NotEmpty @Min(1) Long id, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        CarClass carClass = carClassService.read(id);
        return new ResponseEntity<>(carClass, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> readAll() {
        List<CarClass> carClasses = carClassService.readAll();
        return new ResponseEntity<>(carClasses, HttpStatus.OK);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @PostMapping
    public ResponseEntity<CarClass> create(@Valid @RequestBody CarClassCreateRequest request, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        CarClass carClass = conversionService.convert(request, CarClass.class);
        CarClass createdCarClass = carClassService.create(carClass);
        return new ResponseEntity<>(createdCarClass, HttpStatus.CREATED);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @PutMapping
    public ResponseEntity<CarClass> update(@Valid @RequestBody CarClassUpdateRequest request, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        CarClass carClass = conversionService.convert(request, CarClass.class);
        CarClass updatedCarClass = carClassService.update(carClass);
        return new ResponseEntity<>(updatedCarClass, HttpStatus.OK);

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") @NotEmpty @Min(1) Long id, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        carClassService.delete(id);
    }
}
