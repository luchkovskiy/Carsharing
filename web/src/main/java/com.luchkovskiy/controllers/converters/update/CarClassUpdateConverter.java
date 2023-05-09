package com.luchkovskiy.controllers.converters.update;

import com.luchkovskiy.controllers.converters.base.CarClassBaseConverter;
import com.luchkovskiy.controllers.requests.update.CarClassUpdateRequest;
import com.luchkovskiy.models.CarClass;
import com.luchkovskiy.service.CarClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarClassUpdateConverter extends CarClassBaseConverter<CarClassUpdateRequest, CarClass> {

    private final CarClassService carClassService;

    @Override
    public CarClass convert(CarClassUpdateRequest request) {

        CarClass carClass = carClassService.read(request.getId());

        return doConvert(carClass, request);
    }


}
