package com.luchkovskiy.controllers;

import com.luchkovskiy.controllers.exceptions.ErrorMessage;
import com.luchkovskiy.controllers.requests.create.PaymentCardCreateRequest;
import com.luchkovskiy.controllers.requests.update.PaymentCardUpdateRequest;
import com.luchkovskiy.models.PaymentCard;
import com.luchkovskiy.service.PaymentCardService;
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
@RequestMapping("/rest/payment_cards")
@RequiredArgsConstructor
@Validated
@Tag(name = "Payment Card Controller", description = "This controller allows basic CRUD operations for Payment cards and other functionality")
public class PaymentCardController {

    private final PaymentCardService paymentCardService;

    private final ConversionService conversionService;

    @Operation(
            summary = "Spring Data Find Payment Card By Id",
            description = "This method returns a payment card from the database by the given Id",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Payment card successfully loaded",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentCard.class))
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Payment card doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<PaymentCard> read(@PathVariable("id") @Parameter(description = "Payment card ID in database", required = true, example = "1")
                                            @NotNull @Min(1) Long id) {
        PaymentCard paymentCard = paymentCardService.read(id);
        return new ResponseEntity<>(paymentCard, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Find All Payment Cards",
            description = "This method returns an array of all payment cards in the database",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Payment cards successfully loaded",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PaymentCard.class)))
                    ),
            }
    )
    @GetMapping
    public ResponseEntity<Object> readAll() {
        List<PaymentCard> paymentCards = paymentCardService.readAll();
        return new ResponseEntity<>(paymentCards, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Create New Payment Card",
            description = "This method adds new payment card in database and returns it with generated ID",
            parameters = {
                    @Parameter(name = "cardNumber", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1234123412341234", type = "string",
                                    description = "Payment card main number")),
                    @Parameter(name = "expirationDate", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "03/24", type = "string",
                                    description = "Card's expiration date")),
                    @Parameter(name = "cvv", in = ParameterIn.QUERY,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "123", type = "string",
                                    description = "Card's CVV")),
                    @Parameter(name = "cardholder", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "ALEKSEY LUCHKOVSKIY", type = "string",
                                    description = "Full name of the cardholder"))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Payment card successfully added",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PaymentCard.class)))
                    ),
            }
    )
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @PostMapping
    public ResponseEntity<PaymentCard> create(@Valid @Parameter(hidden = true) @ModelAttribute PaymentCardCreateRequest request, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        PaymentCard paymentCard = conversionService.convert(request, PaymentCard.class);
        PaymentCard createdPaymentCard = paymentCardService.create(paymentCard);
        return new ResponseEntity<>(createdPaymentCard, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Spring Data Update Payment Card",
            description = "This method updates an existing payment card and returns it from database",
            parameters = {
                    @Parameter(name = "id", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "Id of the payment card")),
                    @Parameter(name = "cardNumber", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1234123412341234", type = "string",
                                    description = "Payment card main number")),
                    @Parameter(name = "expirationDate", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "03/24", type = "string",
                                    description = "Card's expiration date")),
                    @Parameter(name = "cvv", in = ParameterIn.QUERY,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "123", type = "string",
                                    description = "Card's CVV")),
                    @Parameter(name = "cardholder", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "ALEKSEY LUCHKOVSKIY", type = "string",
                                    description = "Full name of the cardholder"))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Payment card successfully updated",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PaymentCard.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Payment card doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @PutMapping
    public ResponseEntity<PaymentCard> update(@Valid @Parameter(hidden = true) @ModelAttribute PaymentCardUpdateRequest request, BindingResult bindingResult) {
        ExceptionChecker.check(bindingResult);
        PaymentCard paymentCard = conversionService.convert(request, PaymentCard.class);
        PaymentCard updatedPaymentCard = paymentCardService.update(paymentCard);
        return new ResponseEntity<>(updatedPaymentCard, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Delete Payment Card",
            description = "This method deletes payment card from database by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Payment card deleted"
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Payment card doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") @Parameter(description = "Payment card ID in database", required = true, example = "1")
                       @Min(1) @NotNull Long id) {
        paymentCardService.delete(id);
    }
}
