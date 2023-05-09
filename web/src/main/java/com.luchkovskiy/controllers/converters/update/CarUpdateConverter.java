package com.luchkovskiy.controllers.converters.update;

import com.luchkovskiy.controllers.converters.base.CarBaseConverter;
import com.luchkovskiy.controllers.requests.update.CarUpdateRequest;
import com.luchkovskiy.models.Car;
import com.luchkovskiy.service.CarClassService;
import com.luchkovskiy.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarUpdateConverter extends CarBaseConverter<CarUpdateRequest, Car> {

    private final CarService carService;

    private final CarClassService carClassService;

    @Override
    public Car convert(CarUpdateRequest request) {

        Car car = carService.read(request.getId());
        car.setCarClass(carClassService.read(request.getClassId()));

        return doConvert(car, request);
    }

}
