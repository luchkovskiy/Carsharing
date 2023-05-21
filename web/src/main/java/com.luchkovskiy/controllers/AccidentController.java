package com.luchkovskiy.controllers;

import com.luchkovskiy.controllers.exceptions.ErrorMessage;
import com.luchkovskiy.controllers.requests.create.AccidentCreateRequest;
import com.luchkovskiy.controllers.requests.update.AccidentUpdateRequest;
import com.luchkovskiy.models.Accident;
import com.luchkovskiy.service.AccidentService;
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
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/rest/accidents")
@RequiredArgsConstructor
@Validated
@Tag(name = "Accident Controller", description = "This controller allows basic CRUD operations for Accidents and other functionality")
public class AccidentController {

    private final AccidentService accidentService;

    private final ConversionService conversionService;

    @Operation(
            summary = "Spring Data Find Accident By Id",
            description = "This method returns an accident from the database by the given Id",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Accident successfully loaded",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Accident.class))
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Accident doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Accident> read(@PathVariable("id") @Parameter(description = "Accident ID in database", required = true, example = "1")
                                         @NotNull @Min(1) Long id) {
        Accident accident = accidentService.read(id);
        return new ResponseEntity<>(accident, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Find All Accidents",
            description = "This method returns an array of all accidents in the database",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Accidents successfully loaded",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Accident.class)))
                    ),
            }
    )
    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Object> readAll() {
        List<Accident> accidents = accidentService.readAll();
        return new ResponseEntity<>(accidents, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Create New Accident",
            description = "This method adds new accident in database and returns it with generated ID",
            parameters = {
                    @Parameter(name = "sessionId", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "Id of the session in which the accident happened")),
                    @Parameter(name = "name", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Car issue", type = "string",
                                    description = "Name of accident")),
                    @Parameter(name = "time", in = ParameterIn.QUERY,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-02-22T17:24:01", type = "date-time",
                                    description = "The time when the accident happened")),
                    @Parameter(name = "damageLevel", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2", type = "integer",
                                    description = "Car damage scale from 1 to 5")),
                    @Parameter(name = "critical", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "true", type = "boolean",
                                    description = "Does the car need repair after the incident?"))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Accident successfully added",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Accident.class)))
                    ),
            }
    )
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<Accident> create(@Valid @Parameter(hidden = true) @ModelAttribute AccidentCreateRequest request, BindingResult bindingResult) {
        ExceptionChecker.validCheck(bindingResult);
        Accident accident = conversionService.convert(request, Accident.class);
        Accident createdAccident = accidentService.create(accident);
        return new ResponseEntity<>(createdAccident, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Spring Data Update Accident",
            description = "This method updates an existing accident and returns it from database",
            parameters = {
                    @Parameter(name = "id", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "Id of the accident")),
                    @Parameter(name = "sessionId", in = ParameterIn.QUERY,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "Id of the session in which the accident happened")),
                    @Parameter(name = "name", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Car issue", type = "string",
                                    description = "Name of accident")),
                    @Parameter(name = "time", in = ParameterIn.QUERY,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-02-22T17:24:01", type = "date-time",
                                    description = "The time when the accident happened")),
                    @Parameter(name = "damageLevel", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2", type = "integer",
                                    description = "Car damage scale from 1 to 5")),
                    @Parameter(name = "critical", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "true", type = "boolean",
                                    description = "Does the car need repair after the incident?"))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Accident successfully updated",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Accident.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Accident doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    @PutMapping
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Accident> update(@Valid @Parameter(hidden = true) @ModelAttribute AccidentUpdateRequest request, BindingResult bindingResult) {
        ExceptionChecker.validCheck(bindingResult);
        Accident accident = conversionService.convert(request, Accident.class);
        Accident updatedAccident = accidentService.update(accident);
        return new ResponseEntity<>(updatedAccident, HttpStatus.OK);

    }

    @Operation(
            summary = "Spring Data Delete Accident",
            description = "This method deletes accident from database by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Accident deleted"
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Accident doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    public void delete(@PathVariable("id") @Parameter(description = "Accident ID in database", required = true, example = "1") @Min(1) @NotNull Long id) {
        accidentService.delete(id);
    }

}
