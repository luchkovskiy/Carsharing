package com.luchkovskiy.controllers;

import com.luchkovskiy.controllers.exceptions.ErrorMessage;
import com.luchkovskiy.controllers.requests.create.RoleCreateRequest;
import com.luchkovskiy.controllers.requests.update.RoleUpdateRequest;
import com.luchkovskiy.models.Role;
import com.luchkovskiy.models.SystemRole;
import com.luchkovskiy.models.User;
import com.luchkovskiy.service.RoleService;
import com.luchkovskiy.service.UserService;
import com.luchkovskiy.util.ExceptionChecker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/roles")
@RequiredArgsConstructor
@Validated
@Tag(name = "Role Controller", description = "This controller allows basic CRUD operations for Roles and other functionality")
public class RoleController {

    private final RoleService roleService;

    private final UserService userService;

    private final ConversionService conversionService;

    @Operation(
            summary = "Spring Data Find Role By Id",
            description = "This method returns a role from the database by the given Id",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Role successfully loaded",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Role.class))
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Role doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Role> read(@PathVariable("id") @Parameter(description = "Role ID in database", required = true, example = "1")
                                     @NotNull @Min(1) Long id) {
        Role role = roleService.read(id);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Find All Roles",
            description = "This method returns an array of all roles in the database",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Roles successfully loaded",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Role.class)))
                    ),
            }
    )
    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Map<String, Object>> getAllRoles() {
        List<Role> roles = roleService.readAll();
        return new ResponseEntity<>(Collections.singletonMap("roles", roles), HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Find User Authorities",
            description = "This method returns a map of user authorities by user Id",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "User authorities successfully loaded"
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Role doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @GetMapping("/auth/{userId}")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Map<String, Object>> getUsersAuthorities(@PathVariable @Parameter(description = "User id ID in database", required = true, example = "1")
                                                                   @NotNull @Min(1) Long userId) {
        User user = userService.read(userId);
        List<Role> roles = roleService.getUserAuthorities(userId);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("user", user);
        result.put("roles", roles);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Create New Role",
            description = "This method adds new role in database and returns it with generated ID",
            parameters = {
                    @Parameter(name = "systemRole", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "ROLE_USER", type = "systemRoles",
                                    implementation = SystemRole.class, description = "User's role")),
                    @Parameter(name = "userId", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "User's Id in database"))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Role successfully added",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Role.class)))
                    ),
            }
    )
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<Role> create(@Valid @Parameter(hidden = true) @ModelAttribute RoleCreateRequest request, BindingResult bindingResult) {
        ExceptionChecker.validCheck(bindingResult);
        Role role = conversionService.convert(request, Role.class);
        Role createdRole = roleService.create(role);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Spring Data Update Role",
            description = "This method updates an existing role and returns it from database",
            parameters = {
                    @Parameter(name = "id", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "Id of the role")),
                    @Parameter(name = "systemRole", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "ROLE_USER", type = "systemRoles",
                                    implementation = SystemRole.class, description = "User's role")),
                    @Parameter(name = "userId", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "User's Id in database"))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Role successfully updated",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Role.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Role doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @PutMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<Role> update(@Valid @Parameter(hidden = true) @ModelAttribute RoleUpdateRequest request, BindingResult bindingResult) {
        ExceptionChecker.validCheck(bindingResult);
        Role role = conversionService.convert(request, Role.class);
        Role updatedRole = roleService.update(role);
        return new ResponseEntity<>(updatedRole, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Delete Role",
            description = "This method deletes role from database by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Role deleted"
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Role doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    public void delete(@PathVariable("id") @Parameter(description = "Role ID in database", required = true, example = "1")
                       @Min(1) @NotNull Long id) {
        roleService.delete(id);
    }

}
