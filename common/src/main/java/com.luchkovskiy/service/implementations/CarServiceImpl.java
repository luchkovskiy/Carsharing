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
        return carRepository.findById(id).orElseThrow(() -> new RuntimeException("Info not found!"));
    }

    @Override
    public List<Car> readAll() {
        return carRepository.findAll();
    }

    @Override
    public Car create(Car object) {
        return carRepository.save(object);
    }

    @Override
    public Car update(Car object) {
        if (!carRepository.existsById(object.getId()))
            throw new RuntimeException();
        return carRepository.save(object);
    }

    @Override
    public void delete(Long id) {
        if (!carRepository.existsById(id))
            throw new RuntimeException();
        carRepository.deleteById(id);
    }
}
