package com.luchkovskiy.controllers.converters.create;

import com.luchkovskiy.controllers.converters.base.CarClassBaseConverter;
import com.luchkovskiy.controllers.requests.create.CarClassCreateRequest;
import com.luchkovskiy.models.CarClass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CarClassCreateConverter extends CarClassBaseConverter<CarClassCreateRequest, CarClass> {

    @Override
    public CarClass convert(CarClassCreateRequest request) {

        CarClass carClass = new CarClass();

        carClass.setCreated(LocalDateTime.now());

        return doConvert(carClass, request);
    }

}
