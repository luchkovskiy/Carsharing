package com.luchkovskiy.controllers;


import com.luchkovskiy.controllers.requests.UserCreateRequest;
import com.luchkovskiy.models.User;
import com.luchkovskiy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> read(@PathVariable("id") Long id) {
        User user = userService.read(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> readAll() {
        List<User> users = userService.readAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody UserCreateRequest request) {
        User createdUser = userService.create(getUser(request));
        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<User> update(@RequestBody UserCreateRequest request) {
        User updatedUser = userService.update(getUser(request));
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }

    private User getUser(UserCreateRequest request) {
        return User.builder()
                .id(request.getId())
                .name(request.getName())
                .surname(request.getSurname())
                .birthdayDate(request.getBirthdayDate())
                .active(request.getActive())
                .address(request.getAddress())
                .passportId(request.getPassportId())
                .driverId(request.getDriverId())
                .drivingExperience(request.getDrivingExperience())
                .rating(request.getRating())
                .accountBalance(request.getAccountBalance())
                .build();
    }
}
