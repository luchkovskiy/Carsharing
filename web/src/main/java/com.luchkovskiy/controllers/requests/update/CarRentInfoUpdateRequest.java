package com.luchkovskiy.controllers.requests.update;

import com.luchkovskiy.controllers.requests.create.CarRentInfoCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Validated
public class CarRentInfoUpdateRequest extends CarRentInfoCreateRequest {

    @NotEmpty
    @Min(1)
    private Long id;

}
