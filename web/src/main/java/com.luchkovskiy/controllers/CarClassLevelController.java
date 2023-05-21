package com.luchkovskiy.controllers;

import com.luchkovskiy.controllers.exceptions.ErrorMessage;
import com.luchkovskiy.controllers.requests.create.CarClassLevelCreateRequest;
import com.luchkovskiy.controllers.requests.update.CarClassLevelUpdateRequest;
import com.luchkovskiy.models.CarClassLevel;
import com.luchkovskiy.models.enums.CarComfortType;
import com.luchkovskiy.service.CarClassLevelService;
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
@RequestMapping("/rest/cars/classes")
@RequiredArgsConstructor
@Validated
@Tag(name = "Car class Controller", description = "This controller allows basic CRUD operations for Car classes and other functionality")
public class CarClassLevelController {

    private final CarClassLevelService carClassLevelService;

    private final ConversionService conversionService;

    @Operation(
            summary = "Spring Data Find Car class By Id",
            description = "This method returns a car class from the database by the given Id",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Car class successfully loaded",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarClassLevel.class))
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Car class doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<CarClassLevel> read(@PathVariable("id")
                                              @Parameter(description = "Car class ID in database", required = true, example = "1") @NotNull @Min(1) Long id) {
        CarClassLevel carClassLevel = carClassLevelService.read(id);
        return new ResponseEntity<>(carClassLevel, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Find All Car classes",
            description = "This method returns an array of all car classes in the database",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Car classes successfully loaded",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CarClassLevel.class)))
                    ),
            }
    )
    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Object> readAll() {
        List<CarClassLevel> carClassLevels = carClassLevelService.readAll();
        return new ResponseEntity<>(carClassLevels, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Create New Car class",
            description = "This method adds new car class in database and returns it with generated ID",
            parameters = {
                    @Parameter(name = "name", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Business", type = "string",
                                    description = "Name of car class")),
                    @Parameter(name = "accessLevel", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "Access level to be used by user subscription from 1 to 3")),
                    @Parameter(name = "comfortType", in = ParameterIn.QUERY,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "High", type = "string",
                                    implementation = CarComfortType.class, description = "Comfort level of current class: may be basic, comfort or high")),
                    @Parameter(name = "pricePerHour", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "16.4", type = "number",
                                    description = "Current price for the car rent"))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Car class successfully added",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CarClassLevel.class)))
                    ),
            }
    )
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<CarClassLevel> create(@Valid @Parameter(hidden = true) @ModelAttribute CarClassLevelCreateRequest request, BindingResult bindingResult) {
        ExceptionChecker.validCheck(bindingResult);
        CarClassLevel carClassLevel = conversionService.convert(request, CarClassLevel.class);
        CarClassLevel createdCarClassLevel = carClassLevelService.create(carClassLevel);
        return new ResponseEntity<>(createdCarClassLevel, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Spring Data Update Car class",
            description = "This method updates an existing car class and returns it from database",
            parameters = {
                    @Parameter(name = "id", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "Id of the car class")),
                    @Parameter(name = "name", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Business", type = "string",
                                    description = "Name of car class")),
                    @Parameter(name = "accessLevel", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "Access level to be used by user subscription from 1 to 3")),
                    @Parameter(name = "comfortType", in = ParameterIn.QUERY,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "High", type = "string",
                                    implementation = CarComfortType.class, description = "Comfort level of current class: may be basic, comfort or high")),
                    @Parameter(name = "pricePerHour", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "16.4", type = "number",
                                    description = "Current price for the car rent"))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Car class successfully updated",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CarClassLevel.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Car class doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    @PutMapping
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<CarClassLevel> update(@Valid @Parameter(hidden = true) @ModelAttribute CarClassLevelUpdateRequest request, BindingResult bindingResult) {
        ExceptionChecker.validCheck(bindingResult);
        CarClassLevel carClassLevel = conversionService.convert(request, CarClassLevel.class);
        CarClassLevel updatedCarClassLevel = carClassLevelService.update(carClassLevel);
        return new ResponseEntity<>(updatedCarClassLevel, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Delete Car Class",
            description = "This method deletes car class from database by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Car class deleted"
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Car class doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    public void delete(@PathVariable("id") @Parameter(description = "Car class ID in database", required = true, example = "1") @Min(1) @NotNull Long id) {
        carClassLevelService.delete(id);
    }
}
