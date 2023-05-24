package com.luchkovskiy.controllers.converters.update;

import com.luchkovskiy.controllers.converters.base.CarBaseConverter;
import com.luchkovskiy.controllers.requests.update.CarUpdateRequest;
import com.luchkovskiy.models.Car;
import com.luchkovskiy.service.CarClassLevelService;
import com.luchkovskiy.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarUpdateConverter extends CarBaseConverter<CarUpdateRequest, Car> {

    private final CarService carService;

    private final CarClassLevelService carClassLevelService;

    @Override
    public Car convert(CarUpdateRequest request) {

        Car car = carService.findById(request.getId());
        car.setCarClassLevel(carClassLevelService.findById(request.getClassId()));
        car.setVisible(request.getVisible());
        return doConvert(car, request);
    }

}
