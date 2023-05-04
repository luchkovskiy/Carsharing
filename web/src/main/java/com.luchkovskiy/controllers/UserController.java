package com.luchkovskiy.controllers;


import com.luchkovskiy.controllers.requests.create.UserCreateRequest;
import com.luchkovskiy.controllers.requests.update.*;
import com.luchkovskiy.models.*;
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
        User user = new User();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setBirthdayDate(request.getBirthdayDate());
        user.setActive(request.getActive());
        user.setAddress(request.getAddress());
        user.setPassportId(request.getPassportId());
        user.setDriverId(request.getDriverId());
        user.setDrivingExperience(request.getDrivingExperience());
        user.setRating(request.getRating());
        user.setAccountBalance(request.getAccountBalance());
        user.setAuthenticationInfo(new AuthenticationInfo(request.getEmail(), request.getPassword()));
        User createdUser = userService.create(user);
        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<User> update(@RequestBody UserUpdateRequest request) {
        User readedUser = userService.read(request.getId());
        readedUser.setId(request.getId());
        readedUser.setName(request.getName());
        readedUser.setSurname(request.getSurname());
        readedUser.setBirthdayDate(request.getBirthdayDate());
        readedUser.setActive(request.getActive());
        readedUser.setAddress(request.getAddress());
        readedUser.setPassportId(request.getPassportId());
        readedUser.setDriverId(request.getDriverId());
        readedUser.setDrivingExperience(request.getDrivingExperience());
        readedUser.setRating(request.getRating());
        readedUser.setAccountBalance(request.getAccountBalance());
        User updatedUser = userService.update(readedUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }

}
