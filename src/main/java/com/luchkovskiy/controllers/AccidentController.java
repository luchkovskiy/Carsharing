package com.luchkovskiy.controllers;

import com.luchkovskiy.controllers.requests.AccidentCreateRequest;
import com.luchkovskiy.models.Accident;
import com.luchkovskiy.service.AccidentService;
import com.luchkovskiy.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping
    public ResponseEntity<Accident> create(@RequestBody AccidentCreateRequest request) {
        Accident createdAccident = accidentService.create(getAccident(request));
        return new ResponseEntity<>(createdAccident, HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<Accident> update(@RequestBody AccidentCreateRequest request) {
        Accident updatedAccident = accidentService.update(getAccident(request));
        return new ResponseEntity<>(updatedAccident, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        accidentService.delete(id);
    }

    private Accident getAccident(AccidentCreateRequest request) {
        return Accident.builder()
                .id(request.getId())
                .session(sessionService.read(request.getSessionId()))
                .name(request.getName())
                .fine(request.getFine())
                .time(request.getTime())
                .ratingSubtraction(request.getRatingSubtraction())
                .damageLevel(request.getDamageLevel())
                .critical(request.getCritical())
                .build();
    }
}
