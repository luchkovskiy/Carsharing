package com.luchkovskiy.controllers.requests.update;

import com.luchkovskiy.controllers.requests.create.CarRentInfoCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class CarRentInfoUpdateRequest extends CarRentInfoCreateRequest {

    private Long id;

}
