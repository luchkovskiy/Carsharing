package com.luchkovskiy.controllers.requests.create;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Validated
@Schema(description = "Payment card information to save in database")
public class PaymentCardCreateRequest {

    @NotNull
    @Pattern(regexp = "^\\d{16}$")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1234123412341234", type = "String", description = "Payment card main number")
    private String cardNumber;

    @NotNull
    @Pattern(regexp = "^(0[1-9]|1[0-2])(/)([0-9]{2})$")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "03/24", type = "String", description = "Card's expiration date")
    private String expirationDate;

    @NotNull
    @Pattern(regexp = "\\d{3}")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "123", type = "String", description = "Card's CVV")
    private String cvv;

    @NotNull
    @Size(min = 4, max = 100)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "ALEKSEY LUCHKOVSKIY", type = "String", description = "Full name of the cardholder")
    private String cardholder;

}
