package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.CarRentInfo;
import com.luchkovskiy.repository.CarRentInfoRepository;
import com.luchkovskiy.repository.CarRepository;
import com.luchkovskiy.service.CarRentInfoService;
import com.luchkovskiy.service.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarRentInfoServiceImpl implements CarRentInfoService {

    private final CarRentInfoRepository carRentInfoRepository;

    private final CarRepository carRepository;

    private final EntityManager entityManager;

    @Override
    public CarRentInfo findById(Long id) {
        return carRentInfoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Car info not found!"));
    }

    @Override
    public List<CarRentInfo> findAll() {
        return carRentInfoRepository.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public CarRentInfo create(CarRentInfo object) {
        validCheck(object);
        if (carRentInfoRepository.findCarRentInfoByCarId(object.getCar().getId()) != null) {
            throw new RuntimeException("You can't create a second info for the same car");
        } else {
            return carRentInfoRepository.save(object);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public CarRentInfo update(CarRentInfo object) {
        entityManager.clear();
        validCheck(object);
        if (!carRentInfoRepository.existsById(object.getId()))
            throw new EntityNotFoundException("Car info not found!");
        return carRentInfoRepository.save(object);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public void delete(Long id) {
        if (!carRentInfoRepository.existsById(id))
            throw new EntityNotFoundException("Car info not found!");
        carRentInfoRepository.deleteCarRentInfo(id);
    }

    @Override
    public CarRentInfo findByCarId(Long carId) {
        CarRentInfo carRentInfo;
        try {
            carRentInfo = carRentInfoRepository.findCarRentInfoByCarId(carId);
        } catch (Exception e) {
            throw new EntityNotFoundException("Car info not found!");
        }
        return carRentInfo;
    }

    private void validCheck(CarRentInfo object) {
        carCheck(object);
        availableCheck(object);
    }

    private void carCheck(CarRentInfo carRentInfo) {
        if (!carRepository.existsById(carRentInfo.getCar().getId())) {
            throw new EntityNotFoundException("Car not found");
        }
    }

    private void availableCheck(CarRentInfo carRentInfo) {
        if (carRentInfo.getAvailable() && carRentInfo.getRepairing()) {
            throw new RuntimeException("Car can't repairing and be available at the same time");
        }
    }

}
