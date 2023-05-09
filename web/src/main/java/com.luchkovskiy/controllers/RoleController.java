package com.luchkovskiy.controllers;

import com.luchkovskiy.controllers.exceptions.IllegalRequestException;
import com.luchkovskiy.controllers.requests.create.RoleCreateRequest;
import com.luchkovskiy.controllers.requests.update.RoleUpdateRequest;
import com.luchkovskiy.models.Role;
import com.luchkovskiy.models.User;
import com.luchkovskiy.service.RoleService;
import com.luchkovskiy.service.UserService;
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
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    private final UserService userService;

    private final ConversionService conversionService;

    @GetMapping("/{id}")
    public ResponseEntity<Role> read(@PathVariable("id") Long id) {
        Role role = roleService.read(id);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllRoles() {
        List<Role> roles = roleService.readAll();
        return new ResponseEntity<>(Collections.singletonMap("roles", roles), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> getUsersAuthorities(@PathVariable Long userId) {

        User user = userService.read(userId);
        List<Role> roles = roleService.getUserAuthorities(userId);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("user", user);
        result.put("roles", roles);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Role> create(@Valid @RequestBody RoleCreateRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalRequestException(bindingResult);
        }
        Role role = conversionService.convert(request, Role.class);
        Role createdRole = roleService.create(role);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Role> update(@RequestBody RoleUpdateRequest request) {
        Role role = conversionService.convert(request, Role.class);
        Role updatedRole = roleService.update(role);
        return new ResponseEntity<>(updatedRole, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        roleService.delete(id);
    }
}
