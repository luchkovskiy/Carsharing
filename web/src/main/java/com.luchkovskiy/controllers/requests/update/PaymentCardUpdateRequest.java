package com.luchkovskiy.controllers.requests.update;

import com.luchkovskiy.controllers.requests.create.PaymentCardCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class PaymentCardUpdateRequest extends PaymentCardCreateRequest {

    private Long id;


}
