package com.luchkovskiy.controllers;

import com.luchkovskiy.controllers.exceptions.ErrorMessage;
import com.luchkovskiy.controllers.requests.create.SessionCreateRequest;
import com.luchkovskiy.controllers.requests.update.SessionUpdateRequest;
import com.luchkovskiy.models.Session;
import com.luchkovskiy.service.SessionService;
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
import java.util.List;


@RestController
@RequestMapping("/sessions")
@RequiredArgsConstructor
@Validated
@Tag(name = "Session Controller", description = "This controller allows basic CRUD operations for Sessions and other functionality")
public class SessionController {

    private final SessionService sessionService;
    private final ConversionService conversionService;

    @Operation(
            summary = "Spring Data Find Session By Id",
            description = "This method returns a session from the database by the given Id",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Session successfully loaded",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Session.class))
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Session doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Session> read(@PathVariable("id") @Parameter(description = "Session ID in database", required = true, example = "1")
                                        @NotNull @Min(1) Long id) {
        Session session = sessionService.read(id);
        return new ResponseEntity<>(session, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Find All Sessions",
            description = "This method returns an array of all sessions in the database",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Sessions successfully loaded",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Session.class)))
                    ),
            }
    )
    @GetMapping
    public ResponseEntity<Object> readAll() {
        List<Session> sessions = sessionService.readAll();
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Create New Session",
            description = "This method adds new session in database and returns it with generated ID",
            parameters = {
                    @Parameter(name = "userId", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "User's Id in database")),
                    @Parameter(name = "carId", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "Car's Id in database")),
                    @Parameter(name = "startTime", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-02-22T17:24:01", type = "date-time",
                                    description = "The time when the session started")),
                    @Parameter(name = "endTime", in = ParameterIn.QUERY,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-02-22T19:14:56", type = "date-time",
                                    description = "The time when the session ended")),
                    @Parameter(name = "status", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Active", type = "string",
                                    description = "Status of the session")),
                    @Parameter(name = "distancePassed", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "15.5", type = "number",
                                    description = "Amount of kilometers passed during the session"))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Session successfully added",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Session.class)))
                    ),
            }
    )
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @PostMapping
    public ResponseEntity<Session> create(@Valid @Parameter(hidden = true) @ModelAttribute SessionCreateRequest request, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        Session session = conversionService.convert(request, Session.class);
        Session createdSession = sessionService.create(session);
        return new ResponseEntity<>(createdSession, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Spring Data Update Session",
            description = "This method updates an existing session and returns it from database",
            parameters = {
                    @Parameter(name = "id", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "Id of the session")),
                    @Parameter(name = "userId", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "User's Id in database")),
                    @Parameter(name = "carId", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "Car's Id in database")),
                    @Parameter(name = "startTime", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-02-22T17:24:01", type = "date-time",
                                    description = "The time when the session started")),
                    @Parameter(name = "endTime", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-02-22T19:14:56", type = "date-time",
                                    description = "The time when the session ended")),
                    @Parameter(name = "status", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Active", type = "string",
                                    description = "Status of the session")),
                    @Parameter(name = "distancePassed", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "15.5", type = "number",
                                    description = "Amount of kilometers passed during the session"))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Session successfully updated",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Session.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Session doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @PutMapping
    public ResponseEntity<Session> update(@Valid @Parameter(hidden = true) @ModelAttribute SessionUpdateRequest request, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        Session session = conversionService.convert(request, Session.class);
        Session updatedSession = sessionService.update(session);
        return new ResponseEntity<>(updatedSession, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Delete Session",
            description = "This method deletes session from database by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Session deleted"
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Session doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") @Parameter(description = "Session ID in database", required = true, example = "1")
                       @Min(1) @NotNull Long id) {
        sessionService.delete(id);
    }

}
