package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.Car;
import com.luchkovskiy.models.CarRentInfo;
import com.luchkovskiy.repository.CarRentInfoRepository;
import com.luchkovskiy.repository.CarRepository;
import com.luchkovskiy.service.CarService;
import com.luchkovskiy.service.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    private final CarRentInfoRepository carRentInfoRepository;

    @Override
    public Car read(Long id) {
        return carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Car not found!"));
    }

    @Override
    public List<Car> readAll() {
        return carRepository.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public Car create(Car object) {
        Car car = carRepository.save(object);
        CarRentInfo carRentInfo = new CarRentInfo();
        carRentInfo.setCar(car);
        carRentInfo.setGasRemaining(50f);
        carRentInfo.setCondition(5f);
        carRentInfo.setCurrentLocation("1st Ring 11, Minsk");
        carRentInfoRepository.save(carRentInfo);
        return car;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public Car update(Car object) {
        if (!carRepository.existsById(object.getId()))
            throw new EntityNotFoundException("Car not found!");
        return carRepository.save(object);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public void delete(Long id) {
        if (!carRepository.existsById(id))
            throw new EntityNotFoundException("Car not found!");
        carRepository.inactiveCar(id);
    }
}
