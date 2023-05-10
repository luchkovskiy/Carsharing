package com.luchkovskiy.controllers.requests.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Validated
public class PaymentCardCreateRequest {

    @NotBlank
    @Min(16)
    private String cardNumber;

    @NotBlank
    private String expirationDate;

    @NotBlank
    private String cvv;

    @NotBlank
    private String cardholder;
}
