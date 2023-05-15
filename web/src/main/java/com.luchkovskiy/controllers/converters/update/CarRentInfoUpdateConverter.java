package com.luchkovskiy.controllers.converters.update;

import com.luchkovskiy.controllers.converters.base.CarRentInfoBaseConverter;
import com.luchkovskiy.controllers.requests.update.CarRentInfoUpdateRequest;
import com.luchkovskiy.models.CarRentInfo;
import com.luchkovskiy.service.CarRentInfoService;
import com.luchkovskiy.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarRentInfoUpdateConverter extends CarRentInfoBaseConverter<CarRentInfoUpdateRequest, CarRentInfo> {

    private final CarRentInfoService carRentInfoService;

    private final CarService carService;

    @Override
    public CarRentInfo convert(CarRentInfoUpdateRequest request) {

        CarRentInfo carRentInfo = carRentInfoService.read(request.getId());
        carRentInfo.setCar(carService.read(request.getCarId()));

        return doConvert(carRentInfo, request);
    }
}
