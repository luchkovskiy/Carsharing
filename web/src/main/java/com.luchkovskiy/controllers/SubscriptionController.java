package com.luchkovskiy.controllers;


import com.luchkovskiy.controllers.exceptions.ErrorMessage;
import com.luchkovskiy.controllers.requests.create.SubscriptionCreateRequest;
import com.luchkovskiy.controllers.requests.update.SubscriptionUpdateRequest;
import com.luchkovskiy.models.Subscription;
import com.luchkovskiy.models.enums.StatusType;
import com.luchkovskiy.service.SubscriptionService;
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
@RequestMapping("/rest/subscriptions")
@RequiredArgsConstructor
@Validated
@Tag(name = "Subscription Controller", description = "This controller allows basic CRUD operations for Subscriptions and other functionality")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    private final ConversionService conversionService;

    @Operation(
            summary = "Spring Data Find Subscription By Id",
            description = "This method returns a subscription from the database by the given Id",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Subscription successfully loaded",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Subscription.class))
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Subscription doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Subscription> read(@PathVariable("id") @Parameter(description = "Subscription ID in database", required = true, example = "1")
                                             @NotNull @Min(1) Long id) {
        Subscription subscription = subscriptionService.read(id);
        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Find All Subscriptions",
            description = "This method returns an array of all subscriptions in the database",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Subscriptions successfully loaded",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Subscription.class)))
                    ),
            }
    )
    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Object> readAll() {
        List<Subscription> subscriptions = subscriptionService.readAll();
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Create New Subscription",
            description = "This method adds new subscription in database and returns it with generated ID",
            parameters = {
                    @Parameter(name = "userId", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "User's Id in database")),
                    @Parameter(name = "daysTotal", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2", type = "integer",
                                    description = "Amount of days the subscription was active")),
                    @Parameter(name = "levelId", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "Subscription's level Id"))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Subscription successfully added",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Subscription.class)))
                    ),
            }
    )
    @PostMapping
    public ResponseEntity<Subscription> create(@Valid @Parameter(hidden = true) @ModelAttribute SubscriptionCreateRequest request, BindingResult bindingResult) {
        ExceptionChecker.validCheck(bindingResult);
        Subscription subscription = conversionService.convert(request, Subscription.class);
        Subscription createdSubscription = subscriptionService.create(subscription);
        return new ResponseEntity<>(createdSubscription, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Spring Data Update Subscription",
            description = "This method updates an existing subscription and returns it from database",
            parameters = {
                    @Parameter(name = "id", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "Id of the subscription")),
                    @Parameter(name = "userId", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "User's Id in database")),
                    @Parameter(name = "startTime", in = ParameterIn.QUERY,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-02-22T17:24:01", type = "date-time",
                                    description = "The time when the subscription started")),
                    @Parameter(name = "endTime", in = ParameterIn.QUERY,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-02-24T17:24:01", type = "date-time",
                                    description = "The time when the subscription ended")),
                    @Parameter(name = "status", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "ACTIVE", type = "statusType",
                                    implementation = StatusType.class, description = "Subscription status")),
                    @Parameter(name = "tripsAmount", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "5", type = "integer",
                                    description = "Amount of trips during the current subscription")),
                    @Parameter(name = "daysTotal", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2", type = "integer",
                                    description = "Amount of days the subscription was active")),
                    @Parameter(name = "levelId", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "Subscription's level Id"))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Subscription successfully updated",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Subscription.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Subscription doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @PutMapping
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Subscription> update(@Valid @Parameter(hidden = true) @ModelAttribute SubscriptionUpdateRequest request, BindingResult bindingResult) {
        ExceptionChecker.validCheck(bindingResult);
        Subscription subscription = conversionService.convert(request, Subscription.class);
        Subscription updatedSubscription = subscriptionService.update(subscription);
        return new ResponseEntity<>(updatedSubscription, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Delete Subscription",
            description = "This method deletes subscription from database by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Subscription deleted"
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Subscription doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    public void delete(@PathVariable("id") @Parameter(description = "Subscription ID in database", required = true, example = "1")
                       @Min(1) @NotNull Long id) {
        subscriptionService.delete(id);
    }

}
