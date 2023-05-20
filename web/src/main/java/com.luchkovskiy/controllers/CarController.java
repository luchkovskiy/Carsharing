package com.luchkovskiy.controllers;

import com.luchkovskiy.controllers.exceptions.ErrorMessage;
import com.luchkovskiy.controllers.requests.create.CarCreateRequest;
import com.luchkovskiy.controllers.requests.update.CarUpdateRequest;
import com.luchkovskiy.models.Car;
import com.luchkovskiy.service.CarService;
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
@RequestMapping("/rest/cars")
@RequiredArgsConstructor
@Validated
@Tag(name = "Car Controller", description = "This controller allows basic CRUD operations for Cars and other functionality")
public class CarController {

    private final CarService carService;

    private final ConversionService conversionService;

    @Operation(
            summary = "Spring Data Find Car By Id",
            description = "This method returns a car from the database by the given Id",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Car successfully loaded",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class))
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Car doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Car> read(@PathVariable("id") @Parameter(description = "Car ID in database", required = true, example = "1")
                                    @NotNull @Min(1) Long id) {
        Car car = carService.read(id);
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Find All Cars",
            description = "This method returns an array of all cars in the database",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Cars successfully loaded",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Car.class)))
                    ),
            }
    )
    @GetMapping
    public ResponseEntity<Object> readAll() {
        List<Car> cars = carService.readAll();
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Create New Car",
            description = "This method adds new car in database and returns it with generated ID",
            parameters = {
                    @Parameter(name = "brand", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Audi", type = "string",
                                    description = "Brand of the car")),
                    @Parameter(name = "model", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "RS7", type = "string",
                                    description = "Model of the car")),
                    @Parameter(name = "maxSpeed", in = ParameterIn.QUERY,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "210.5", type = "number",
                                    description = "Maximum car's speed")),
                    @Parameter(name = "color", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Blue", type = "string",
                                    description = "Color of the car")),
                    @Parameter(name = "releaseYear", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2018", type = "integer",
                                    description = "Year when the car was released")),
                    @Parameter(name = "gearboxType", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Mechanical", type = "string",
                                    description = "Gearbox type of the car")),
                    @Parameter(name = "sitsAmount", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "5", type = "integer",
                                    description = "Amount of sits in the car")),
                    @Parameter(name = "classId", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "Id of the class level in database")),
                    @Parameter(name = "gasConsumption", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "7.5", type = "number",
                                    description = "Fuel consumption per 100 km in average")),
                    @Parameter(name = "licensePlateNumber", in = ParameterIn.QUERY,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1234AX-3", type = "string",
                                    description = "License plate number of the car"))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Car successfully added",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Car.class)))
                    ),
            }
    )
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @PostMapping
    public ResponseEntity<Car> create(@Valid @Parameter(hidden = true) @ModelAttribute CarCreateRequest request, BindingResult bindingResult) {
        ExceptionChecker.validCheck(bindingResult);
        Car car = conversionService.convert(request, Car.class);
        Car createdCar = carService.create(car);
        return new ResponseEntity<>(createdCar, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Spring Data Update Car",
            description = "This method updates an existing car and returns it from database",
            parameters = {
                    @Parameter(name = "id", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "Id of the car")),
                    @Parameter(name = "brand", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Audi", type = "string",
                                    description = "Brand of the car")),
                    @Parameter(name = "model", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "RS7", type = "string",
                                    description = "Model of the car")),
                    @Parameter(name = "visible", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "false", type = "boolean",
                                    description = "Is car visible in the system?")),
                    @Parameter(name = "maxSpeed", in = ParameterIn.QUERY,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "210.5", type = "number",
                                    description = "Maximum car's speed")),
                    @Parameter(name = "color", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Blue", type = "string",
                                    description = "Color of the car")),
                    @Parameter(name = "releaseYear", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2018", type = "integer",
                                    description = "Year when the car was released")),
                    @Parameter(name = "gearboxType", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Mechanical", type = "string",
                                    description = "Gearbox type of the car")),
                    @Parameter(name = "sitsAmount", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "5", type = "integer",
                                    description = "Amount of sits in the car")),
                    @Parameter(name = "classId", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "Id of the class level in database")),
                    @Parameter(name = "gasConsumption", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "7.5", type = "number",
                                    description = "Fuel consumption per 100 km in average")),
                    @Parameter(name = "licensePlateNumber", in = ParameterIn.QUERY,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1234AX-3", type = "string",
                                    description = "License plate number of the car"))

            },
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Car successfully updated",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Car.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Car doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @PutMapping
    public ResponseEntity<Car> update(@Valid @Parameter(hidden = true) @ModelAttribute CarUpdateRequest request, BindingResult bindingResult) {
        ExceptionChecker.validCheck(bindingResult);
        Car car = conversionService.convert(request, Car.class);
        Car updatedCar = carService.update(car);
        return new ResponseEntity<>(updatedCar, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Delete Car",
            description = "This method deletes car from database by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Car deleted"
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Car doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") @Parameter(description = "Car ID in database", required = true, example = "1")
                       @Min(1) @NotNull Long id) {
        carService.delete(id);
    }

}
