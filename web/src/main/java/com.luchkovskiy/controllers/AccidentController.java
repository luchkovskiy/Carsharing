package com.luchkovskiy.controllers;

import com.luchkovskiy.controllers.requests.create.AccidentCreateRequest;
import com.luchkovskiy.controllers.requests.update.AccidentUpdateRequest;
import com.luchkovskiy.models.Accident;
import com.luchkovskiy.service.AccidentService;
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
@RequestMapping("/accidents")
@RequiredArgsConstructor
@Validated
public class AccidentController {


    private final AccidentService accidentService;

    private final ConversionService conversionService;

    @GetMapping("/{id}")
    public ResponseEntity<Accident> read(@PathVariable("id") @NotEmpty @Min(1) Long id, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        Accident accident = accidentService.read(id);
        return new ResponseEntity<>(accident, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> readAll() {
        List<Accident> accidents = accidentService.readAll();
        return new ResponseEntity<>(accidents, HttpStatus.OK);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @PostMapping
    public ResponseEntity<Accident> create(@Valid @RequestBody AccidentCreateRequest request, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        Accident accident = conversionService.convert(request, Accident.class);
        Accident createdAccident = accidentService.create(accident);
        return new ResponseEntity<>(createdAccident, HttpStatus.CREATED);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @PutMapping
    public ResponseEntity<Accident> update(@Valid @RequestBody AccidentUpdateRequest request, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        Accident accident = conversionService.convert(request, Accident.class);
        Accident updatedAccident = accidentService.update(accident);
        return new ResponseEntity<>(updatedAccident, HttpStatus.OK);

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") @NotEmpty @Min(1) Long id, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        accidentService.delete(id);
    }

}
