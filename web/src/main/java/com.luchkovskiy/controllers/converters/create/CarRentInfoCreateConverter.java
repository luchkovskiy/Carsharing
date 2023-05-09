package com.luchkovskiy.controllers.converters.create;

import com.luchkovskiy.controllers.converters.base.CarRentInfoBaseConverter;
import com.luchkovskiy.controllers.requests.create.CarRentInfoCreateRequest;
import com.luchkovskiy.models.CarRentInfo;
import com.luchkovskiy.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CarRentInfoCreateConverter extends CarRentInfoBaseConverter<CarRentInfoCreateRequest, CarRentInfo> {

    private final CarService carService;

    @Override
    public CarRentInfo convert(CarRentInfoCreateRequest request) {

        CarRentInfo carRentInfo = new CarRentInfo();

        carRentInfo.setCar(carService.read(request.getCarId()));
        carRentInfo.setCreated(LocalDateTime.now());


        return doConvert(carRentInfo, request);
    }
}
