package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.Car;
import com.luchkovskiy.models.CarRentInfo;
import com.luchkovskiy.repository.CarClassLevelRepository;
import com.luchkovskiy.repository.CarRentInfoRepository;
import com.luchkovskiy.repository.CarRepository;
import com.luchkovskiy.service.CarService;
import com.luchkovskiy.service.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    private final CarRentInfoRepository carRentInfoRepository;

    private final CarClassLevelRepository carClassLevelRepository;

    private final EntityManager entityManager;

    @Override
    public Car findById(Long id) {
        return carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Car not found!"));
    }

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public Car create(Car object) {
        validCheck(object);
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
        entityManager.clear();
        if (!carRepository.existsById(object.getId()))
            throw new EntityNotFoundException("Car not found!");
        validCheck(object);
        return carRepository.save(object);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public void delete(Long id) {
        if (!carRepository.existsById(id))
            throw new EntityNotFoundException("Car not found!");
        carRepository.inactiveCar(id);
    }

    @Override
    public Page<Car> findAllCarsByPage(Pageable pageable) {
        return carRepository.findAll(pageable);
    }

    private void validCheck(Car car) {
        classLevelCheck(car);
        licensePlateNumberCheck(car);
    }

    private void licensePlateNumberCheck(Car car) {
        if (carRepository.existsByLicensePlateNumber(car.getLicensePlateNumber())) {
            throw new RuntimeException("This license plate number already exists in database");
        }
    }

    private void classLevelCheck(Car car) {
        if (!carClassLevelRepository.existsById(car.getCarClassLevel().getId())) {
            throw new EntityNotFoundException("Car class level not found");
        }
    }
}
