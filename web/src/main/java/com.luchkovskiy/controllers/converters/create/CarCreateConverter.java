package com.luchkovskiy.controllers.converters.create;

import com.luchkovskiy.controllers.converters.base.CarBaseConverter;
import com.luchkovskiy.controllers.requests.create.CarCreateRequest;
import com.luchkovskiy.models.Car;
import com.luchkovskiy.service.CarClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CarCreateConverter extends CarBaseConverter<CarCreateRequest, Car> {

    private final CarClassService carClassService;

    @Override
    public Car convert(CarCreateRequest request) {

        Car car = new Car();

        car.setCreated(LocalDateTime.now());
        car.setVisible(true);
        car.setCarClass(carClassService.read(request.getClassId()));

        return doConvert(car, request);
    }

}
