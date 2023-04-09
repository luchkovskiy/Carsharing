package com.luchkovskiy.controllers;

import com.luchkovskiy.controllers.requests.SessionCreateRequest;
import com.luchkovskiy.models.Session;
import com.luchkovskiy.service.SessionService;
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
        Session createdSession = sessionService.create(getSession(request));
        return new ResponseEntity<>(createdSession, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<Session> update(@RequestBody SessionCreateRequest request) {
        Session updatedSession = sessionService.update(getSession(request));
        return new ResponseEntity<>(updatedSession, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        sessionService.delete(id);
    }

    private static Session getSession(SessionCreateRequest request) {
        return Session.builder()
                .id(request.getId())
                .user(request.getUser())
                .car(request.getCar())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .distancePassed(request.getDistancePassed())
                .build();
    }
}
