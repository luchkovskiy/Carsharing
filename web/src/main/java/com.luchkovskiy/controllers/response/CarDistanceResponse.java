package com.luchkovskiy.controllers.response;

import com.google.maps.model.DistanceMatrix;
import com.luchkovskiy.models.Car;
import lombok.Data;

@Data
public class CarDistanceResponse {

    private Car car;

    private DistanceMatrix distanceMatrix;

    public CarDistanceResponse(Car car, DistanceMatrix distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
        this.car = car;
    }

}
