package com.luchkovskiy.controllers.converters.update;

import com.luchkovskiy.controllers.converters.base.CarClassLevelBaseConverter;
import com.luchkovskiy.controllers.requests.update.CarClassLevelUpdateRequest;
import com.luchkovskiy.models.CarClassLevel;
import com.luchkovskiy.service.CarClassLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarClassLevelUpdateConverter extends CarClassLevelBaseConverter<CarClassLevelUpdateRequest, CarClassLevel> {

    private final CarClassLevelService carClassLevelService;

    @Override
    public CarClassLevel convert(CarClassLevelUpdateRequest request) {

        CarClassLevel carClassLevel = carClassLevelService.read(request.getId());

        return doConvert(carClassLevel, request);
    }


}
