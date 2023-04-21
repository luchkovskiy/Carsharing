package com.luchkovskiy.controllers;

import com.luchkovskiy.controllers.requests.create.SessionCreateRequest;
import com.luchkovskiy.controllers.requests.update.*;
import com.luchkovskiy.models.Session;
import com.luchkovskiy.service.CarService;
import com.luchkovskiy.service.SessionService;
import com.luchkovskiy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;
    private final UserService userService;
    private final CarService carService;

    @GetMapping("/{id}")
    public ResponseEntity<Session> read(@PathVariable("id") Long id) {
        Session session = sessionService.read(id);
        return new ResponseEntity<>(session, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> readAll() {
        List<Session> sessions = sessionService.readAll();
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Session> create(@RequestBody SessionCreateRequest request) {
        Session session = new Session();
        session.setUser(userService.read(request.getUserId()));
        session.setCar(carService.read(request.getCarId()));
        session.setStartTime(request.getStartTime());
        session.setEndTime(request.getEndTime());
        session.setDistancePassed(request.getDistancePassed());
        Session createdSession = sessionService.create(session);
        return new ResponseEntity<>(createdSession, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Session> update(@RequestBody SessionUpdateRequest request) {
        Session readedSession = sessionService.read(request.getId());
        readedSession.setId(request.getId());
        readedSession.setUser(userService.read(request.getUserId()));
        readedSession.setCar(carService.read(request.getCarId()));
        readedSession.setStartTime(request.getStartTime());
        readedSession.setEndTime(request.getEndTime());
        readedSession.setDistancePassed(request.getDistancePassed());
        Session updatedSession = sessionService.update(readedSession);
        return new ResponseEntity<>(updatedSession, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        sessionService.delete(id);
    }

}
