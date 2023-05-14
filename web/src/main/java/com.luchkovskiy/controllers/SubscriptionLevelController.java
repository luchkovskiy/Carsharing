package com.luchkovskiy.controllers;

import com.luchkovskiy.controllers.exceptions.ErrorMessage;
import com.luchkovskiy.controllers.requests.create.SubscriptionLevelCreateRequest;
import com.luchkovskiy.controllers.requests.update.SubscriptionLevelUpdateRequest;
import com.luchkovskiy.models.SubscriptionLevel;
import com.luchkovskiy.service.SubscriptionLevelService;
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
@RequestMapping("/subscriptions/levels")
@RequiredArgsConstructor
@Validated
@Tag(name = "Subscription Level Controller", description = "This controller allows basic CRUD operations for Subscription levels")
public class SubscriptionLevelController {

    private final SubscriptionLevelService subscriptionLevelService;

    private final ConversionService conversionService;

    @Operation(
            summary = "Spring Data Find Subscription Level By Id",
            description = "This method returns a subscription level from the database by the given Id",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Subscription level successfully loaded",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SubscriptionLevel.class))
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Subscription level doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionLevel> read(@PathVariable("id") @Parameter(description = "Subscription level ID in database", required = true, example = "1")
                                                  @NotNull @Min(1) Long id) {
        SubscriptionLevel subscriptionLevel = subscriptionLevelService.read(id);
        return new ResponseEntity<>(subscriptionLevel, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Find All Subscription levels",
            description = "This method returns an array of all subscription levels in the database",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Subscription levels successfully loaded",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SubscriptionLevel.class)))
                    ),
            }
    )
    @GetMapping
    public ResponseEntity<Object> readAll() {
        List<SubscriptionLevel> subscriptionLevels = subscriptionLevelService.readAll();
        return new ResponseEntity<>(subscriptionLevels, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Create New Subscription Level",
            description = "This method adds new subscription level in database and returns it with generated ID",
            parameters = {
                    @Parameter(name = "accessLevel", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2", type = "integer",
                                    description = "Subscription's access level")),
                    @Parameter(name = "pricePerDay", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "97.5", type = "number",
                                    description = "Subscription's price per day")),
                    @Parameter(name = "name", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Standard", type = "string",
                                    description = "Subscription's level name"))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Subscription level successfully added",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SubscriptionLevel.class)))
                    ),
            }
    )
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @PostMapping
    public ResponseEntity<SubscriptionLevel> create(@Valid @Parameter(hidden = true) @ModelAttribute SubscriptionLevelCreateRequest request, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        SubscriptionLevel subscriptionLevel = conversionService.convert(request, SubscriptionLevel.class);
        SubscriptionLevel createdSubscriptionLevel = subscriptionLevelService.create(subscriptionLevel);
        return new ResponseEntity<>(createdSubscriptionLevel, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Spring Data Update Subscription Level",
            description = "This method updates an existing subscription level and returns it from database",
            parameters = {
                    @Parameter(name = "id", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "Id of the subscription level")),
                    @Parameter(name = "accessLevel", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2", type = "integer",
                                    description = "Subscription's access level")),
                    @Parameter(name = "pricePerDay", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "97.5", type = "number",
                                    description = "Subscription's price per day")),
                    @Parameter(name = "name", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Standard", type = "string",
                                    description = "Subscription's level name"))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Subscription level successfully updated",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SubscriptionLevel.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Subscription level doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @PutMapping
    public ResponseEntity<SubscriptionLevel> update(@Valid @Parameter(hidden = true) @ModelAttribute SubscriptionLevelUpdateRequest request, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        SubscriptionLevel subscriptionLevel = conversionService.convert(request, SubscriptionLevel.class);
        SubscriptionLevel updatedSubscriptionLevel = subscriptionLevelService.update(subscriptionLevel);
        return new ResponseEntity<>(updatedSubscriptionLevel, HttpStatus.OK);

    }

    @Operation(
            summary = "Spring Data Delete Subscription Level",
            description = "This method deletes subscription level from database by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Subscription level deleted"
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Subscription level doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") @Parameter(description = "Subscription level ID in database", required = true, example = "1")
                       @Min(1) @NotNull Long id) {
        subscriptionLevelService.delete(id);
    }

}
