package com.luchkovskiy.controllers.converters.base;

import com.luchkovskiy.controllers.requests.create.CarRentInfoCreateRequest;
import com.luchkovskiy.models.CarRentInfo;
import org.springframework.core.convert.converter.Converter;

public abstract class CarRentInfoBaseConverter<S, T> implements Converter<S, T> {

    public CarRentInfo doConvert(CarRentInfo carRentInfoForUpdate, CarRentInfoCreateRequest request) {

        carRentInfoForUpdate.setGasRemaining(request.getGasRemaining());
        carRentInfoForUpdate.setRepairing(request.getRepairing());
        carRentInfoForUpdate.setCurrentLocation(request.getCurrentLocation());
        carRentInfoForUpdate.setAvailable(request.getAvailable());
        carRentInfoForUpdate.setCondition(request.getCondition());

        return carRentInfoForUpdate;
    }

}
