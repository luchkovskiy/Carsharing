package com.luchkovskiy.controllers;

import com.luchkovskiy.controllers.exceptions.*;
import com.luchkovskiy.controllers.requests.create.AccidentCreateRequest;
import com.luchkovskiy.controllers.requests.update.AccidentUpdateRequest;
import com.luchkovskiy.models.Accident;
import com.luchkovskiy.service.AccidentService;
import com.luchkovskiy.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.List;

@RestController
@RequestMapping("/accidents")
@RequiredArgsConstructor
public class AccidentController {

    private final AccidentService accidentService;
    private final SessionService sessionService;

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

    // TODO: 18.04.2023 Не забыть конвертер

    @PostMapping
    public ResponseEntity<Accident> create(@Valid @RequestBody AccidentCreateRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new IllegalRequestException(bindingResult);
        }

        Accident accident = new Accident();
        accident.setSession(sessionService.read(request.getSessionId()));
        accident.setName(request.getName());
        accident.setFine(request.getFine());
        accident.setTime(request.getTime());
        accident.setRatingSubtraction(request.getRatingSubtraction());
        accident.setDamageLevel(request.getDamageLevel());
        accident.setCritical(request.getCritical());
        Accident createdAccident = accidentService.create(accident);
        return new ResponseEntity<>(createdAccident, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Accident> update(@RequestBody AccidentUpdateRequest request) {
        Accident readedAccident = accidentService.read(request.getId());
        readedAccident.setId(request.getId());
        readedAccident.setSession(sessionService.read(request.getSessionId()));
        readedAccident.setName(request.getName());
        readedAccident.setFine(request.getFine());
        readedAccident.setTime(request.getTime());
        readedAccident.setRatingSubtraction(request.getRatingSubtraction());
        readedAccident.setDamageLevel(request.getDamageLevel());
        readedAccident.setCritical(request.getCritical());
        Accident updatedAccident = accidentService.update(readedAccident);
        return new ResponseEntity<>(updatedAccident, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        accidentService.delete(id);
    }

}
