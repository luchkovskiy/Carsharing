package com.luchkovskiy.controllers.converters.base;

import com.luchkovskiy.controllers.requests.create.AccidentCreateRequest;
import com.luchkovskiy.models.Accident;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;

public abstract class AccidentBaseConverter<S, T> implements Converter<S, T> {


    public Accident doConvert(Accident accidentForUpdate, AccidentCreateRequest request) {

        accidentForUpdate.setName(request.getName());
        accidentForUpdate.setName(request.getName());
        accidentForUpdate.setTime(request.getTime());
        accidentForUpdate.setRatingSubtraction(request.getRatingSubtraction());
        accidentForUpdate.setDamageLevel(request.getDamageLevel());
        accidentForUpdate.setCritical(request.getCritical());
        accidentForUpdate.setChanged(LocalDateTime.now());
        return accidentForUpdate;
    }
}
