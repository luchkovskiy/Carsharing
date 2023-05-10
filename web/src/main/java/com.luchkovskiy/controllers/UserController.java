package com.luchkovskiy.controllers;


import com.luchkovskiy.controllers.requests.create.UserCreateRequest;
import com.luchkovskiy.controllers.requests.update.UserUpdateRequest;
import com.luchkovskiy.models.User;
import com.luchkovskiy.service.UserService;
import com.luchkovskiy.util.ExceptionChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    private final ConversionService conversionService;

    @GetMapping("/{id}")
    public ResponseEntity<User> read(@PathVariable("id") @NotEmpty @Min(1) Long id, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        User user = userService.read(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> readAll() {
        List<User> users = userService.readAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody UserCreateRequest request, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        User user = conversionService.convert(request, User.class);
        User createdUser = userService.create(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<User> update(@Valid @RequestBody UserUpdateRequest request, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        User user = conversionService.convert(request, User.class);
        User updatedUser = userService.update(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") @NotEmpty @Min(1) Long id, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        userService.delete(id);
    }

}
