package com.luchkovskiy.controllers;

import com.luchkovskiy.controllers.exceptions.IllegalRequestException;
import com.luchkovskiy.controllers.requests.create.AccidentCreateRequest;
import com.luchkovskiy.controllers.requests.update.AccidentUpdateRequest;
import com.luchkovskiy.models.Accident;
import com.luchkovskiy.service.AccidentService;
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
@RequestMapping("/accidents")
@RequiredArgsConstructor
public class AccidentController {


    private final AccidentService accidentService;

    private final ConversionService conversionService;

    @GetMapping("/{id}")
    public ResponseEntity<Accident> read(@PathVariable("id") Long id) {
        Accident accident = accidentService.read(id);
        return new ResponseEntity<>(accident, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> readAll() {
        List<Accident> accidents = accidentService.readAll();
        return new ResponseEntity<>(accidents, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Accident> create(@Valid @RequestBody AccidentCreateRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalRequestException(bindingResult);
        }
        Accident accident = conversionService.convert(request, Accident.class);
        Accident createdAccident = accidentService.create(accident);
        return new ResponseEntity<>(createdAccident, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Accident> update(@RequestBody AccidentUpdateRequest request) {
        Accident accident = conversionService.convert(request, Accident.class);
        Accident updatedAccident = accidentService.update(accident);
        return new ResponseEntity<>(updatedAccident, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        accidentService.delete(id);
    }

}
