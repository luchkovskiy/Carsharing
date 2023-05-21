package com.luchkovskiy.controllers.converters.create;

import com.luchkovskiy.controllers.converters.base.CarClassLevelBaseConverter;
import com.luchkovskiy.controllers.requests.create.CarClassLevelCreateRequest;
import com.luchkovskiy.models.CarClassLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarClassLevelCreateConverter extends CarClassLevelBaseConverter<CarClassLevelCreateRequest, CarClassLevel> {

    @Override
    public CarClassLevel convert(CarClassLevelCreateRequest request) {

        CarClassLevel carClassLevel = new CarClassLevel();

        return doConvert(carClassLevel, request);
    }

}
