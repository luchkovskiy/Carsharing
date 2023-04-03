package com.luchkovskiy.controllers;

import com.luchkovskiy.controllers.requests.AccidentCreateRequest;
import com.luchkovskiy.models.Accident;
import com.luchkovskiy.service.AccidentService;
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
    public ResponseEntity<Object> create(@RequestBody AccidentCreateRequest request) {
        Accident accident = Accident.builder()
                .session(request.getSession())
                .name(request.getName())
                .fine(request.getFine())
                .time(request.getTime())
                .ratingSubtraction(request.getRatingSubtraction())
                .damageLevel(request.getDamageLevel())
                .critical(request.getCritical())
                .build();
        Accident createdAccident = accidentService.create(accident);
        return new ResponseEntity<>(createdAccident, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<Object> update(@RequestBody Accident accident) {
        Accident updatedAccident = accidentService.update(accident);
        return new ResponseEntity<>(updatedAccident, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        accidentService.delete(id);
    }
}