package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.Car;
import com.luchkovskiy.repository.CarRepository;
import com.luchkovskiy.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    @Override
    public Car read(Long id) {
        if (!carRepository.checkIdValid(id))
            throw new RuntimeException();
        return carRepository.read(id);
    }

    @Override
    public List<Car> readAll() {
        return carRepository.readAll();
    }

    @Override
    public Car create(Car object) {
        return carRepository.create(object);
    }

    @Override
    public Car update(Car object) {
        if (!carRepository.checkIdValid(object.getId()))
            throw new RuntimeException();
        return carRepository.update(object);
    }

    @Override
    public void delete(Long id) {
        if (!carRepository.checkIdValid(id))
            throw new RuntimeException();
        carRepository.delete(id);
    }

    @Override
    public boolean checkIdExist(Long id) {
        return carRepository.checkIdValid(id);
    }
}
