package com.luchkovskiy.controllers.requests.update;

import com.luchkovskiy.controllers.requests.create.CarCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class CarUpdateRequest extends CarCreateRequest {

    private Long id;

}
