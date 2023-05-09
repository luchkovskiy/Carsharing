package com.luchkovskiy.controllers.converters.base;

import com.luchkovskiy.controllers.requests.create.CarClassCreateRequest;
import com.luchkovskiy.models.CarClass;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;

public abstract class CarClassBaseConverter<S, T> implements Converter<S, T> {
    public CarClass doConvert(CarClass carClassForUpdate, CarClassCreateRequest request) {

        carClassForUpdate.setName(request.getName());
        carClassForUpdate.setAccessLevel(request.getAccessLevel());
        carClassForUpdate.setComfortType(request.getComfortType());
        carClassForUpdate.setPricePerHour(request.getPricePerHour());
        carClassForUpdate.setChanged(LocalDateTime.now());

        return carClassForUpdate;
    }
}
