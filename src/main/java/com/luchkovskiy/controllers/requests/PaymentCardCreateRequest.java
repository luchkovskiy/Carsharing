package com.luchkovskiy.controllers.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentCardCreateRequest {
    private Long id;
    private String cardNumber;
    private String expirationDate;
    private String cvv;
    private String cardholder;
}
