package com.luchkovskiy.controllers;

import com.luchkovskiy.controllers.exceptions.ErrorMessage;
import com.luchkovskiy.controllers.requests.create.CarRentInfoCreateRequest;
import com.luchkovskiy.controllers.requests.update.CarRentInfoUpdateRequest;
import com.luchkovskiy.models.CarRentInfo;
import com.luchkovskiy.service.CarRentInfoService;
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
@RequestMapping("/rest/cars/info")
@RequiredArgsConstructor
@Validated
@Tag(name = "Car Rent Info Controller", description = "This controller allows basic CRUD operations for Car rent info")
public class CarRentInfoController {

    private final CarRentInfoService carRentInfoService;

    private final ConversionService conversionService;

    @Operation(
            summary = "Spring Data Find Car Rent Info By Id",
            description = "This method returns a car rent info from the database by the given Id",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Car rent info successfully loaded",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarRentInfo.class))
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Car rent info doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<CarRentInfo> read(@PathVariable("id") @Parameter(description = "Car rent info ID in database", required = true, example = "1")
                                            @NotNull @Min(1) Long id) {
        CarRentInfo carRentInfo = carRentInfoService.read(id);
        return new ResponseEntity<>(carRentInfo, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Find All Cars rent info",
            description = "This method returns an array of all cars rent info in the database",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Cars' rent info successfully loaded",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CarRentInfo.class)))
                    ),
            }
    )
    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Object> readAll() {
        List<CarRentInfo> carRentInfo = carRentInfoService.readAll();
        return new ResponseEntity<>(carRentInfo, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Create New Car Rent Info",
            description = "This method adds new car rent info in database and returns it with generated ID",
            parameters = {
                    @Parameter(name = "carId", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "Id of the car")),
                    @Parameter(name = "gasRemaining", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "12.8", type = "number",
                                    description = "Current balance of fuel")),
                    @Parameter(name = "repairing", in = ParameterIn.QUERY,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "false", type = "boolean",
                                    description = "Is the car repairing right now?")),
                    @Parameter(name = "currentLocation", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Minsk, Dzerzhinskogo 18", type = "string",
                                    description = "Car's current address")),
                    @Parameter(name = "available", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "false", type = "boolean",
                                    description = "Is the car available right now?")),
                    @Parameter(name = "condition", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "4.6", type = "number",
                                    description = "Car's condition level from 1 to 5"))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Car rent info successfully added",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CarRentInfo.class)))
                    ),
            }
    )
    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<CarRentInfo> create(@Valid @Parameter(hidden = true) @ModelAttribute CarRentInfoCreateRequest request, BindingResult bindingResult) {
        ExceptionChecker.validCheck(bindingResult);
        CarRentInfo carRentInfo = conversionService.convert(request, CarRentInfo.class);
        CarRentInfo createdCarRentInfo = carRentInfoService.create(carRentInfo);
        return new ResponseEntity<>(createdCarRentInfo, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Spring Data Update Car Rent Info",
            description = "This method updates an existing car rent info and returns it from database",
            parameters = {
                    @Parameter(name = "id", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "Id of the car rent info")),
                    @Parameter(name = "carId", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "Id of the car")),
                    @Parameter(name = "gasRemaining", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "12.8", type = "number",
                                    description = "Current balance of fuel")),
                    @Parameter(name = "repairing", in = ParameterIn.QUERY,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "false", type = "boolean",
                                    description = "Is the car repairing right now?")),
                    @Parameter(name = "currentLocation", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "53.978527, 30.395270", type = "string",
                                    description = "Latitude and longitude of current car's location")),
                    @Parameter(name = "available", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "false", type = "boolean",
                                    description = "Is the car available right now?")),
                    @Parameter(name = "condition", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "4.6", type = "number",
                                    description = "Car's condition level from 1 to 5"))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Car rent info successfully updated",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CarRentInfo.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Car rent info doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @PutMapping
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Object> update(@Valid @Parameter(hidden = true) @ModelAttribute CarRentInfoUpdateRequest request, BindingResult bindingResult) {
        ExceptionChecker.validCheck(bindingResult);
        CarRentInfo carRentInfo = conversionService.convert(request, CarRentInfo.class);
        CarRentInfo updatedCarRentInfo = carRentInfoService.update(carRentInfo);
        return new ResponseEntity<>(updatedCarRentInfo, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Delete Car Rent Info",
            description = "This method deletes car rent info from database by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Car rent info deleted"
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Car rent info doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    public void delete(@PathVariable("id") @Parameter(description = "Car rent info ID in database", required = true, example = "1")
                       @Min(1) @NotNull Long id) {
        carRentInfoService.delete(id);
    }

}
