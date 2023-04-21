package com.luchkovskiy.controllers.requests.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentCardCreateRequest {

    private String cardNumber;
    private String expirationDate;
    private String cvv;
    private String cardholder;
}
