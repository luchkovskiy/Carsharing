package com.luchkovskiy.controllers.converters.base;

import com.luchkovskiy.controllers.requests.create.CarCreateRequest;
import com.luchkovskiy.models.Car;
import org.springframework.core.convert.converter.Converter;

public abstract class CarBaseConverter<S, T> implements Converter<S, T> {

    public Car doConvert(Car carForUpdate, CarCreateRequest request) {

        carForUpdate.setBrand(request.getBrand());
        carForUpdate.setModel(request.getModel());
        carForUpdate.setMaxSpeed(request.getMaxSpeed());
        carForUpdate.setColor(request.getColor());
        carForUpdate.setReleaseYear(request.getReleaseYear());
        carForUpdate.setGearboxType(request.getGearboxType());
        carForUpdate.setSitsAmount(request.getSitsAmount());
        carForUpdate.setGasConsumption(request.getGasConsumption());
        carForUpdate.setLicensePlateNumber(request.getLicensePlateNumber());
        return carForUpdate;
    }
}
