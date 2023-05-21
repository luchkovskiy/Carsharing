package com.luchkovskiy.controllers.converters.base;

import com.luchkovskiy.controllers.requests.create.CarClassLevelCreateRequest;
import com.luchkovskiy.models.CarClassLevel;
import org.springframework.core.convert.converter.Converter;

public abstract class CarClassLevelBaseConverter<S, T> implements Converter<S, T> {
    public CarClassLevel doConvert(CarClassLevel carClassLevelForUpdate, CarClassLevelCreateRequest request) {

        carClassLevelForUpdate.setName(request.getName());
        carClassLevelForUpdate.setAccessLevel(request.getAccessLevel());
        carClassLevelForUpdate.setComfortType(request.getComfortType());
        carClassLevelForUpdate.setPricePerHour(request.getPricePerHour());
        return carClassLevelForUpdate;
    }
}
