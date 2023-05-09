package com.luchkovskiy.controllers.requests.update;

import com.luchkovskiy.controllers.requests.create.CarClassCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class CarClassUpdateRequest extends CarClassCreateRequest {

    private Long id;

}
