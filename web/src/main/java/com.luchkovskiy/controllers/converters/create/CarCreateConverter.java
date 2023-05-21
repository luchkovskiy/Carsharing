package com.luchkovskiy.controllers.converters.create;

import com.luchkovskiy.controllers.converters.base.CarBaseConverter;
import com.luchkovskiy.controllers.requests.create.CarCreateRequest;
import com.luchkovskiy.models.Car;
import com.luchkovskiy.service.CarClassLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarCreateConverter extends CarBaseConverter<CarCreateRequest, Car> {

    private final CarClassLevelService carClassLevelService;

    @Override
    public Car convert(CarCreateRequest request) {

        Car car = new Car();

        car.setCarClassLevel(carClassLevelService.read(request.getClassId()));

        return doConvert(car, request);
    }

}
